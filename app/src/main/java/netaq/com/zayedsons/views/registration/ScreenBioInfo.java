package netaq.com.zayedsons.views.registration;


import android.Manifest;
import android.app.DatePickerDialog;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.OnNextFromBioScreen;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.utils.ImageUtils;
import netaq.com.zayedsons.utils.Utils;


public class ScreenBioInfo extends Fragment implements Validator.ValidationListener,
        CountryCodePicker.OnCountryChangeListener, BioInfoView, View.OnClickListener {


    private static final int REQUEST_SELECT_PICTURE = 100;
    private static final int PERMISSION_REQUEST_STORAGE = 101;


    private View view;
    private Unbinder unbinder;

    @BindView(R.id.register_scroll_view)
    ScrollView parentScrollView;

    @BindView(R.id.btn_next)
    Button nextButton;
    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;
    @BindView(R.id.edit_photo)
    CircleImageView editPhoto;

    @BindView(R.id.layout_email)
    TextInputLayout layoutEmail;

    @Email
    @NotEmpty
    @BindView(R.id.field_email)
    EditText fieldEmail;

    @BindView(R.id.layout_pwd)
    TextInputLayout layoutPwd;

    @Password
    @NotEmpty
    @BindView(R.id.field_pwd)
    EditText fieldPwd;


    @BindView(R.id.layout_retype_pwd)
    TextInputLayout layoutRetypePwd;

    @NotEmpty
    @BindView(R.id.field_retype_pwd)
    EditText fieldRetypePwd;


    /************* Names Fields & Layouts Start**********/
    @BindView(R.id.layout_name)
    TextInputLayout layoutFirstName;
    @NotEmpty
    @BindView(R.id.field_name)
    EditText fieldFirstName;


    @BindView(R.id.layout_father_name)
    TextInputLayout layoutFatherName;

    @NotEmpty
    @BindView(R.id.field_father_name)
    EditText fieldFatherName;

    @BindView(R.id.field_middle_name)
    EditText fieldMiddleName;
    @BindView(R.id.layout_middle_name)
    TextInputLayout layout_middle_name;

    @NotEmpty
    @BindView(R.id.field_last_name)
    EditText fieldLastName;
    @BindView(R.id.layout_last_name)
    TextInputLayout layout_lastName;
    /***********Names Fields and Layout end*********/

    @NotEmpty
    @BindView(R.id.field_phone)
    EditText fieldPhone;

    @BindView(R.id.layout_date)
    TextInputLayout layout_date;

    @BindView(R.id.field_date)
    EditText fieldDate;

    @BindView(R.id.layout_emirates_id)
    @Nullable
    TextInputLayout layout_emiratesId;
    @BindView(R.id.field_emirates_id)
    @Nullable
    EditText fieldEmiratesID;

    @BindView(R.id.btn_male)
    Button genderMale;
    @BindView(R.id.btn_female)
    Button genderFemale;

    @BindView(R.id.layout_phone)
    TextInputLayout layout_phone;

    @BindView(R.id.ccp)
    CountryCodePicker countryCodePicker;

    private Validator validator;

    @BindView(R.id.photoProgress)
    ProgressBar photoProgress;
    @BindView(R.id.progress)
    ProgressBar progress;

    @BindView(R.id.gender_title)TextView labelGender;

    private BioInfoPresenter bioInfoPresenter = new BioInfoPresenter(this);

    private int gender = Constants.GENDER_NOT_SELECTED;
    private String phone = "";

    private String userID;
    private String profilePicURL = "";

    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;

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

        userID = UUID.randomUUID().toString();

        initViews();

        defineDatePickerListener();

        return view;
    }


    private void initViews() {

        nextButton.setOnClickListener(new NextButtonListener());

        fieldEmail.addTextChangedListener(new EmailTextChangeListener());
        fieldFirstName.addTextChangedListener(new NameTextChangeListener());
        fieldFatherName.addTextChangedListener(new FatherNameTextListener());
        fieldLastName.addTextChangedListener(new LastNameTextListener());

        fieldPwd.addTextChangedListener(new PasswordTextListener());
        fieldRetypePwd.addTextChangedListener(new ConfirmPwdTextListener());

        genderMale.setOnClickListener(new GenderSelectionListener());
        genderFemale.setOnClickListener(new GenderSelectionListener());
        editPhoto.setOnClickListener(new PhotoEditListener());
        profilePhoto.setOnClickListener(new PhotoEditListener());

        countryCodePicker.setOnCountryChangeListener(this);
    }

    /**
     * A picker for the Birthday Date of the user
     */
    private void defineDatePickerListener(){
        // set the click listener for the DatePicker
        fieldDate.setOnClickListener(this);
        // define the DateSet Listener
         date = (view, year, monthOfYear, dayOfMonth) -> {
             myCalendar.set(Calendar.YEAR, year);
             myCalendar.set(Calendar.MONTH, monthOfYear);
             myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
             updateLabel();
         };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {

            case PERMISSION_REQUEST_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openGallery();
                } else {

                    //No need to handle
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PICTURE && resultCode != 0) {

            Uri imageURI = data.getData();
            //profilePhoto.setImageURI(imageURI);
            try {
                String encodedString = ImageUtils.getEncodedString(getContext(), imageURI);
                bioInfoPresenter.uploadProfilePhoto(userID, encodedString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void openGallery() {

        int permissionGrant = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionGrant == PackageManager.PERMISSION_GRANTED) {
            Intent galleryIntent = NavigationController.getGalleryIntent();

            this.startActivityForResult(galleryIntent, REQUEST_SELECT_PICTURE);
        } else {
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
        for (ValidationError error : errors) {
            View view = error.getView();

            switch (view.getId()) {
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
                    layoutFirstName.setError("Name is required");
                    break;


                case R.id.field_father_name:
                    layout_phone.setErrorEnabled(true);
                    layoutFatherName.setError("Father name is required");
                    break;

                case R.id.field_last_name:
                    //layout_lastName.setErrorEnabled(true);
                    layout_lastName.setError("Last Name is required");
                    break;

                case R.id.field_phone:
                    parentScrollView.scrollTo(0, fieldPhone.getBottom());
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

    @Override
    public void onNetworkUnAvailable() {
        //TODO: Handle No network in Photo Upload
    }

    @Override
    public void onError() {

    }

    @Override
    public void showProgress() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progress.setVisibility(View.GONE);
    }


    @Override
    public void onPhotoUploaded(String fileURL) {
        profilePicURL = fileURL;

        Picasso.with(getContext()).load(fileURL).into(profilePhoto, new Callback() {
            @Override
            public void onSuccess() {
                photoProgress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                photoProgress.setVisibility(View.GONE);
            }
        });


    }

    @Override
    public void showPhotoUploadProgress() {
        photoProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePhotoUploadProgress() {
        photoProgress.setVisibility(View.GONE);
    }

    private void setDummyValues() {
        fieldEmail.setText("sabih@netaq.com");
        fieldPwd.setText("sabih123");
        fieldRetypePwd.setText("sabih123");
        fieldFirstName.setText("Sabih");
        fieldFatherName.setText("Ahmed");
        fieldPhone.setText("123456789");
    }

    public HashMap<String, Object> getBioInfoData() {

        String firstName = fieldFirstName.getText().toString();

        String fatherName = fieldFatherName.getText().toString();

        String middleName = fieldMiddleName.getText().toString();

        String lastName = fieldLastName.getText().toString();

        String email = fieldEmail.getText().toString();

        String date = fieldDate.getText().toString();


        HashMap<String, Object> valuesMap = new HashMap<>();

        valuesMap.put("userID", userID);
        valuesMap.put("gender", gender);
        valuesMap.put("email", email);
        valuesMap.put("name1", firstName);
        valuesMap.put("name2", fatherName);
        valuesMap.put("name3", middleName);
        valuesMap.put("name4", lastName);
        valuesMap.put("profilePic", profilePicURL);
        valuesMap.put("dob", date);
        return valuesMap;


    }

    @Override
    public void onClick(View view) {
        if (view == fieldDate) {
            myCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), R.style.datePicker,
                    date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();
        }
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fieldDate.setText(sdf.format(myCalendar.getTime()));
    }

    /***** Inner Classes / Interfaces **/

    private class NextButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            //Validate the form
            //setDummyValues();
            validator.validate(); //OnValidationSuccess

            validateGender();

        }
    }

    private void validateGender() {
        if(gender == Constants.GENDER_NOT_SELECTED){
            labelGender.setTextSize(14);
            labelGender.setTextColor(ContextCompat.getColor(getContext(),R.color.field_validation_error));
            labelGender.setText("Gender*");

        }else{
            labelGender.setTextColor(ContextCompat.getColor(getContext(),R.color.textColorSecondary));
        }
    }

    private class EmailTextChangeListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String inputData = fieldEmail.getText().toString();
            if (!Utils.isValidEmail(inputData)) {
                layoutEmail.setError(getString(R.string.validation_email_format));
            } else {
                layoutEmail.setError(null);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class NameTextChangeListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            layoutFirstName.setError(null);
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

    private class LastNameTextListener implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            layout_lastName.setError(null);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class PhoneTextListener implements TextWatcher {

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

            labelGender.setTextColor(ContextCompat.getColor(getContext(),R.color.textColorSecondary));

            switch (view.getId()) {

                case R.id.btn_male:
                    genderMale.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_maroon_btn));
                    genderMale.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                    genderFemale.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_hollow_btn));
                    genderFemale.setTextColor(ContextCompat.getColor(getContext(), R.color.maroonish));

                    gender = Constants.GENDER_MALE;

                    break;

                case R.id.btn_female:
                    genderFemale.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_maroon_btn));
                    genderFemale.setTextColor(ContextCompat.getColor(getContext(), R.color.white));

                    genderMale.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.shape_hollow_btn));
                    genderMale.setTextColor(ContextCompat.getColor(getContext(), R.color.maroonish));

                    gender = Constants.GENDER_FEMALE;

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


    private class ConfirmPwdTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String confirmPwdText = charSequence.toString();
            String pwdText = fieldPwd.getText().toString();

            if (!confirmPwdText.isEmpty()) {
                if (!confirmPwdText.equals(pwdText)) {
                    layoutRetypePwd.setError("Passwords do not match");
                } else {
                    layoutRetypePwd.setError("");
                }
            } else {
                layoutRetypePwd.setError("");
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }

    private class PasswordTextListener implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String pwdText = charSequence.toString();
            String confirmPwdText = fieldRetypePwd.getText().toString();

            if (!confirmPwdText.isEmpty()) {

                if (pwdText.equals(confirmPwdText)) {
                    layoutRetypePwd.setError(null);
                }
            }

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    }
}
