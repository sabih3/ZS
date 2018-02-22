package netaq.com.zayedsons.registration;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import com.hbb20.CountryCodePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.OnNextFromBioScreen;
import netaq.com.zayedsons.utils.Utils;


public class ScreenBioInfo extends Fragment implements Validator.ValidationListener, CountryCodePicker.OnCountryChangeListener {


    private static final int REQUEST_SELECT_PICTURE = 100;
    private static final int PERMISSION_REQUEST_STORAGE = 101;


    private View view;
    private Unbinder unbinder;

    @BindView(R.id.register_scroll_view)ScrollView parentScrollView;

    @BindView(R.id.btn_next)Button nextButton;
    @BindView(R.id.profile_photo)CircleImageView profilePhoto;
    @BindView(R.id.edit_photo)CircleImageView editPhoto;

    @BindView(R.id.layout_email)TextInputLayout layoutEmail;

    @Email
    @NotEmpty
    @BindView(R.id.field_email)EditText fieldEmail;

    @BindView(R.id.layout_pwd)TextInputLayout layoutPwd;

    @Password
    @NotEmpty
    @BindView(R.id.field_pwd)EditText fieldPwd;


    @BindView(R.id.layout_retype_pwd)TextInputLayout layoutRetypePwd;

    @NotEmpty
    @BindView(R.id.field_retype_pwd)EditText fieldRetypePwd;

    @BindView(R.id.layout_name)TextInputLayout layoutName;


    @NotEmpty
    @BindView(R.id.field_name)EditText fieldName;

    @BindView(R.id.layout_phone)TextInputLayout layout_phone;


    @NotEmpty
    @BindView(R.id.field_phone)EditText fieldPhone;

    @BindView(R.id.layout_date)TextInputLayout layout_date;

    @BindView(R.id.field_date)EditText fieldDate;

    @BindView(R.id.layout_emirates_id)@Nullable TextInputLayout layout_emiratesId;
    @BindView(R.id.field_emirates_id)@Nullable EditText fieldEmiratesID;

    @BindView(R.id.btn_male)Button genderMale;
    @BindView(R.id.btn_female)Button genderFemale;

    @BindView(R.id.layout_father_name)TextInputLayout layoutFatherName;


    @NotEmpty
    @BindView(R.id.field_father_name)EditText fieldFatherName;

    @BindView(R.id.ccp)CountryCodePicker countryCodePicker;
    private Validator validator;

    public ScreenBioInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.registration_1, container, false);
        validator = new Validator(this);
        validator.setValidationListener(this);

        unbinder = ButterKnife.bind(this, view);
        initViews();

        return view;
    }

    private void initViews() {

        nextButton.setOnClickListener(new NextButtonListener());

        fieldEmail.addTextChangedListener(new EmailTextChangeListener());
        fieldName.addTextChangedListener(new NameTextChangeListener());
        fieldFatherName.addTextChangedListener(new FatherNameTextListener());

        genderMale.setOnClickListener(new GenderSelectionListener());
        genderFemale.setOnClickListener(new GenderSelectionListener());
        editPhoto.setOnClickListener(new PhotoEditListener());
        profilePhoto.setOnClickListener(new PhotoEditListener());

        countryCodePicker.setOnCountryChangeListener(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){

            case PERMISSION_REQUEST_STORAGE:
                if(grantResults.length > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openGallery();
                }else{

                    //No need to handle
                }
            break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_SELECT_PICTURE && resultCode !=0){

            Uri imageURI = data.getData();
            profilePhoto.setImageURI(imageURI);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void openGallery() {

        int permissionGrant = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if(permissionGrant == PackageManager.PERMISSION_GRANTED){
            Intent galleryIntent = NavigationController.getGalleryIntent();

            this.startActivityForResult(galleryIntent, REQUEST_SELECT_PICTURE);
        }else{
            //ask permisssion

            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);
        }
    }

    @Override
    public void onValidationSucceeded() {
        EventBus.getDefault().post(new OnNextFromBioScreen());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for(ValidationError error: errors){
            View view = error.getView();

            switch (view.getId()){
                case R.id.field_email:
                    layoutEmail.setErrorEnabled(true);
                    layoutEmail.setError("Email is required");
                break;

                case R.id.field_pwd:
                    layoutPwd.setErrorEnabled(true);
                    layoutPwd.setError("Password is required");
                break;

                case R.id.field_retype_pwd:
                    layout_phone.setErrorEnabled(true);
                    layoutRetypePwd.setError("Confirm password is required");
                break;

                case R.id.field_name:
                    layout_phone.setErrorEnabled(true);
                    layoutName.setError("Name is required");
                break;


                case R.id.field_father_name:
                    layout_phone.setErrorEnabled(true);
                    layoutFatherName.setError("Father is required");
                break;

                case R.id.field_phone:
                    parentScrollView.scrollTo(0,fieldPhone.getBottom());
                    layout_phone.setErrorEnabled(true);
                    layout_phone.setError("Phone is required");
                break;


            }
        }
    }

    //Country Code Picker
    @Override
    public void onCountrySelected() {
        countryCodePicker.getSelectedCountryCodeWithPlus();
    }

    private class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //Validate the form
            setDummyValues();
            validator.validate(); //OnValidationSuccess




            //OnValidationError
                //Show error
        }
    }

    private void setDummyValues() {
        fieldEmail.setText("sabih@netaq.com");
        fieldPwd.setText("sabih123");
        fieldRetypePwd.setText("sabih123");
        fieldName.setText("Sabih");
        fieldFatherName.setText("Ahmed");
        fieldPhone.setText("123456789");
    }

    private class EmailTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String inputData = fieldEmail.getText().toString();
            if(!Utils.isValidEmail(inputData)){
                layoutEmail.setError(getString(R.string.validation_email_format));
            }else{
                layoutEmail.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class NameTextChangeListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            layoutName.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class FatherNameTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            layoutFatherName.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class PhoneTextListener implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                layout_phone.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class GenderSelectionListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()){

                case R.id.btn_male:
                    genderMale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_maroon_btn));
                    //genderMale.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.maroonish));
                    genderMale.setTextColor(ContextCompat.getColor(getContext(),R.color.white));

                    genderFemale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_hollow_btn));
                    //genderFemale.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.lightGrey));
                    genderFemale.setTextColor(ContextCompat.getColor(getContext(),R.color.maroonish));
                break;

                case R.id.btn_female:
                    genderFemale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_maroon_btn));
                    //genderFemale.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.maroonish));
                    genderFemale.setTextColor(ContextCompat.getColor(getContext(),R.color.white));

                    genderMale.setBackground(ContextCompat.getDrawable(getContext(),R.drawable.shape_hollow_btn));
                    //genderMale.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.lightGrey));
                    genderMale.setTextColor(ContextCompat.getColor(getContext(),R.color.maroonish));
                break;
            }
        }
    }

    private class PhotoEditListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            openGallery();
        }
    }



}
