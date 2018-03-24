package netaq.com.zayedsons.views;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.eventbus.CitySelectEvent;
import netaq.com.zayedsons.eventbus.SponsorSelectEvent;
import netaq.com.zayedsons.model.AccountInfo;
import netaq.com.zayedsons.model.Profile;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.model.requests.RequestRegisterProfile;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.ImageUtils;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by M.Refaat on 3/14/2018.
 */

public class ProfileActivity extends AppCompatActivity implements UpdateProfileView,Validator.ValidationListener{

    private static final int REQUEST_SELECT_PICTURE = 131;
    private static final int PERMISSION_REQUEST_STORAGE = 132;

    @BindView(R.id.profile_coordinator)CoordinatorLayout coordinatorLayout;
    @BindView(R.id.progress_profile)ProgressBar progressBar;
    @BindView(R.id.toolbar_profile)Toolbar toolbar;
    @BindDrawable(R.drawable.ic_arrow_back_black_24px) Drawable backArrow;
    @BindView(R.id.profile_edit_icon) ImageView editProfileIcon;

    @BindView(R.id.profile_photo) CircleImageView profilePhoto;
    @BindView(R.id.edit_photo)CircleImageView editPhoto;
    @BindView(R.id.photoProgress)ProgressBar photoProgress;

    @BindView(R.id.profile_full_name) TextView name;

    @BindView(R.id.profile_layout_email)TextInputLayout layoutEmail;

    @BindView(R.id.profile_email)
    @NotEmpty
    EditText email;

    @BindView(R.id.profile_mobile) EditText mobile;

    @BindView(R.id.profile_layout_first_name)TextInputLayout layoutFirstName;
    @NotEmpty
    @BindView(R.id.profile_first_name) EditText firstName;

    @BindView(R.id.profile_layout_father_name)TextInputLayout layoutFatherName;
    @NotEmpty
    @BindView(R.id.father_name)EditText fatherName;

    @BindView(R.id.middle_name)EditText middleName;

    @BindView(R.id.profile_layout_last_name)TextInputLayout layoutLastName;
    @NotEmpty
    @BindView(R.id.last_name)EditText lastName;

    @BindView(R.id.gender)EditText fieldGender;

    @NotEmpty
    @BindView(R.id.profile_dob) AppCompatEditText dob;

    @BindView(R.id.profile_layout_eID) TextInputLayout layoutEmiratesID;

    @BindView(R.id.profile_EID)AppCompatEditText fieldEmiratesID;

    @NotEmpty
    @BindView(R.id.profile_city) AppCompatEditText city;

    @BindView(R.id.profile_layout_university)TextInputLayout layoutUniversity;
    @NotEmpty
    @BindView(R.id.profile_university) EditText university;

    @BindView(R.id.profile_layout_major)TextInputLayout layoutMajor;
    @NotEmpty
    @BindView(R.id.profile_major) EditText major;

    @NotEmpty
    @BindView(R.id.profile_sponsor) AppCompatEditText sponsor;

    @BindView(R.id.btn_done)ImageView btn_done;

    @BindString(R.string.snackbar_no_network)String errorNetwork;
    @BindString(R.string.action_label_retry)String actionRetry;

    private UpdateProfilePresenter updateProfilePresenter;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private Calendar calendarInstance;

    private String selectedSponsorID = "-1";
    private String selectedCityID = "-1";
    private String userDOB = "";
    private String profilePhotoURL = "";
    private String emiratesID = "";
    private String userEmail = "";
    private String userPhone = "";
    private String userFullName = "";
    private String userFirstName = "";
    private String userFathersName = "";
    private String userMiddleName = "";
    private String userLastName = "";
    private String userGenderString = "";
    private String selectedGenderID = "";
    private String selectedCityString = "";
    private String selectedSponsorString = "";
    private String userUniversity = "";
    private String userMajor = "";

    private Validator validator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        
        // set the values
        setProfileValues();

        editProfileIcon.setOnClickListener(new EditProfileListener());

        updateProfilePresenter = new UpdateProfilePresenter(this,this);

        validator = new Validator(this);
        validator.setValidationListener(this);
        validator.setValidationMode(Validator.Mode.BURST);

        setToolbar();
        handleEditProfileIcon();
        setDoneButtonListener();
        setCityClickListener();
        setSponsorClickListener();
        setDOBClickListener();
        setGenderClickListener();
        setPhotoEditClickListener();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCitySelected(CitySelectEvent citySelectEvent){

        selectedCityString = citySelectEvent.getItem().getTitle();

        if(citySelectEvent.getItem().getID().equals(Constants.EMPTY_GUID)){
            selectedCityID = citySelectEvent.getItem().getIntID();
        }else{
            selectedCityID = citySelectEvent.getItem().getID();
        }


        city.setText(selectedCityString);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSponsorSelected(SponsorSelectEvent sponsorSelectEvent){
        selectedSponsorString = sponsorSelectEvent.getItem().getTitle();

        if(sponsorSelectEvent.getItem().getID().equals(Constants.EMPTY_GUID)){
            selectedSponsorID = sponsorSelectEvent.getItem().getIntID();
        }else{
            selectedSponsorID = sponsorSelectEvent.getItem().getID();
        }
    }


    private void handleEditProfileIcon() {
        //Hide in case of UserType==Organizer

        if(UserManager.getUser().getAccountInfo().getUserTypeID().equals(Constants.USER_TYPE_POWER)){
            editProfileIcon.setVisibility(View.GONE);
        }else{
            editProfileIcon.setVisibility(View.VISIBLE);
        }
    }

    private void setToolbar() {
        toolbar.setNavigationIcon(backArrow);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view ->{

            ProfileActivity.this.finish();
        });

    }

    private void setDoneButtonListener() {
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validator.validate();

            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        layoutEmail.setError("");
        layoutFirstName.setError("");;
        layoutFatherName.setError("");;
        layoutLastName.setError("");;
        layoutUniversity.setError("");;
        layoutMajor.setError("");

        UIUtils.showMessageDialog(ProfileActivity.this,
                "Are you sure to update your profile?",
                "Yes, Proceed",
                "Not now",
                new UIUtils.DialogButtonListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        getValuesAndUpdate();
                    }

                    @Override
                    public void onNegativeButtonClicked() {
                        setProfileValues();
                        resetEditMode();
                    }
                });




        
    }

    private void getValuesAndUpdate(){
        AccountInfo accountInfo = new AccountInfo();
        Profile profile = new Profile();

        RequestRegisterProfile updateRequest = new RequestRegisterProfile();
        updateRequest.setToken(UserManager.getUser().getAuthToken());
        updateRequest.setDeviceID("123");



        userEmail = email.getText().toString();
        userFirstName = firstName.getText().toString();
        userFathersName = fatherName.getText().toString();
        userMiddleName = middleName.getText().toString();
        userLastName = lastName.getText().toString();
        emiratesID = fieldEmiratesID.getText().toString();
        userUniversity = university.getText().toString();
        userMajor = major.getText().toString();



        accountInfo.setUserID(UserManager.getUser().getAccountInfo().getUserID());
        accountInfo.setUserName(UserManager.getUser().getAccountInfo().getUserName());
        accountInfo.setPassword(UserManager.getUser().getAccountInfo().getPassword());
        accountInfo.setGender(Constants.GENDER_MALE);
        accountInfo.setEmail(userEmail);
        accountInfo.setMobileNo(UserManager.getUser().getAccountInfo().getMobileNo());
        accountInfo.setUserTypeID(Constants.USER_TYPE_NORMAL);


        profile.setName1(userFirstName);
        profile.setName2(userFathersName);
        profile.setName3(userMiddleName);
        profile.setName4(userLastName);
        profile.setProfilePic(profilePhotoURL);
        profile.setdOB(userDOB);
        profile.seteIDNo(emiratesID);
        profile.setCityID(selectedCityID);
        profile.setUniversity(userUniversity);
        profile.setCourseMajor(userMajor);
        profile.setSponsorID(selectedSponsorID);

        updateRequest.setAccountInfo(accountInfo);
        updateRequest.setProfile(profile);

        updateProfilePresenter.updateProfile(updateRequest);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        layoutEmail.setError("");
        layoutFirstName.setError("");;
        layoutFatherName.setError("");;
        layoutLastName.setError("");;
        layoutUniversity.setError("");;
        layoutMajor.setError("");

        for (ValidationError error : errors) {
            View view = error.getView();

            switch (view.getId()){


                case R.id.profile_email:
                        layoutEmail.setError(Constants.ValidationErrors.ERROR_EMAIL);
                break;

                case R.id.profile_first_name:
                        layoutFirstName.setError(Constants.ValidationErrors.ERROR_FIRST_NAME);
                break;

                case R.id.father_name:
                        layoutFatherName.setError(Constants.ValidationErrors.ERROR_FATHER_NAME);
                break;

                case R.id.last_name:
                        layoutLastName.setError(Constants.ValidationErrors.ERROR_LAST_NAME);
                break;


                case R.id.profile_university:
                        layoutUniversity.setError(Constants.ValidationErrors.ERROR_UNIVERSITY);
                break;

                case R.id.field_major:
                        layoutMajor.setError(Constants.ValidationErrors.ERROR_MAJOR);
                break;


            }
        }

    }

    private void setPhotoEditClickListener() {

        editPhoto.setOnClickListener(view ->{
            openGallery();
        });

    }

    private void openGallery() {
        int permissionGrant = ContextCompat.checkSelfPermission(ProfileActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        if (permissionGrant == PackageManager.PERMISSION_GRANTED) {
            Intent galleryIntent = NavigationController.getGalleryIntent();

            this.startActivityForResult(galleryIntent, REQUEST_SELECT_PICTURE);
        } else {
            //ask permisssion
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_STORAGE);


        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_STORAGE:
                openGallery();
            break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_SELECT_PICTURE && resultCode!=0){
            if(data!=null){
                Uri imageURI = data.getData();
                //profilePhoto.setImageURI(imageURI);
                try {
                    String encodedString = ImageUtils.getEncodedString(this, imageURI);

                    updateProfilePresenter.uploadProfilePhoto(UserManager.getUser().getAccountInfo()
                                                             .getUserID(),encodedString);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }



    /**
     * To set the user profile credentials
     */
    private void setProfileValues() {
        ResponseRegister user = UserManager.getUser();
        Profile userProfile = user.getProfile();

        if (userProfile == null) {
            userProfile = new Profile();
        }

        selectedSponsorID = userProfile.getSponsorID();
        selectedCityID = userProfile.getCityID();
        userDOB = userProfile.getdOB();

        profilePhotoURL = userProfile.getProfilePic();
        emiratesID = userProfile.geteIDNo();
        userEmail = user.getAccountInfo().getEmail();
        userPhone = user.getAccountInfo().getMobileNo();

        userFullName = user.getAccountInfo().getFullName();
        userFirstName = userProfile.getName1();
        userFathersName = userProfile.getName2();
        userMiddleName = userProfile.getName3();
        userLastName = userProfile.getName4();

        userGenderString = user.getAccountInfo().getGenderCode();
        selectedGenderID = String.valueOf(user.getAccountInfo().getGender());
        userUniversity = userProfile.getUniversity();
        userMajor = userProfile.getCourseMajor();

        selectedCityString = userProfile.getCityName();
        selectedSponsorString = userProfile.getSponsorName();
        // get the user data the DataBase

        // load the profile photo
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Picasso.with(this).load(userProfile.getProfilePic()).
                        error(getDrawable(R.drawable.ic_person_white_24px)).
                        placeholder(getDrawable(R.drawable.ic_person_white_24px)).into(profilePhoto);
            } else {
                Picasso.with(this).load(userProfile.getProfilePic()).into(profilePhoto);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }


        setText(name,userFullName);
        setText(email, userEmail);
        setText(mobile, userPhone);

        setText(firstName, userFirstName);
        setText(fatherName,userFathersName);
        setText(middleName,userMiddleName);
        setText(lastName,userLastName);
        setText(fieldGender,userGenderString);
        setText(dob, userDOB);
        setText(fieldEmiratesID,emiratesID);
        setText(city, selectedCityString);
        setText(university, userUniversity);
        setText(major, userMajor);
        setText(sponsor, selectedSponsorString);
    }

    /**
     * To set the EditText text value and handle null values
     *
     * @param textView the corresponding textView/editText to set the text value to
     * @param value    the corresponding value to assign to the textView/editText
     */
    private void setText(TextView textView, String value) {
        if (value == null || value.isEmpty()) {
            textView.setText(getResources().getString(R.string.na));
            textView.setAlpha(0.7f);
            return;
        }
        try {
            textView.setText(value);
        } catch (NullPointerException npe) {
            textView.setText(getResources().getString(R.string.na));
            textView.setAlpha(0.7f);
        }
    }

    private void resetEditMode() {
        editProfileIcon.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.GONE);
        editPhoto.setVisibility(View.GONE);

        email.setEnabled(false);

        firstName.setEnabled(false);
        fatherName.setEnabled(false);
        middleName.setEnabled(false);
        lastName.setEnabled(false);
        fieldGender.setEnabled(false);
        fieldEmiratesID.setEnabled(false);
        dob.setEnabled(false);
        city.setEnabled(false);
        university.setEnabled(false);
        major.setEnabled(false);

        sponsor.setEnabled(false);


    }

    @Override
    public void onNetworkUnAvailable() {
        UIUtils.showSnackBar(coordinatorLayout, errorNetwork, actionRetry, new UIUtils.SnackBarActionListener() {
            @Override
            public void onSnackBarAction() {
                getValuesAndUpdate();
            }
        });
    }

    @Override
    public void onError(String resolvedError) {

        UIUtils.showSnackBar(coordinatorLayout,resolvedError);
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showPhotoUploadProgress() {
        photoProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePhotoUploadProgress() {

    }


    @Override
    public void onProfilePhotoUploaded(String fileURL) {
        profilePhotoURL = fileURL;


        Picasso.with(this).load(profilePhotoURL).into(profilePhoto, new Callback() {
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
    public void onProfileUpdatedSuccessfully(ResponseRegister responseRegister) {
        resetEditMode();
        UserManager.setUser(responseRegister);
        setProfileValues();
    }




    private class EditProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            editProfileIcon.setVisibility(View.GONE);
            btn_done.setVisibility(View.VISIBLE);
            editPhoto.setVisibility(View.VISIBLE);

            email.requestFocus();

            email.setEnabled(true);
            firstName.setEnabled(true);
            fatherName.setEnabled(true);
            middleName.setEnabled(true);
            lastName.setEnabled(true);

            fieldGender.setEnabled(true);

            fieldEmiratesID.setEnabled(true);

            dob.setEnabled(true);

            city.setEnabled(true);

            university.setEnabled(true);

            major.setEnabled(true);



            sponsor.setEnabled(true);

        }
    }

    private void setGenderClickListener() {


    }

    private void setDOBClickListener() {
        calendarInstance = Calendar.getInstance();

        dob.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(ProfileActivity.this,
                    R.style.datePicker,dateSetListener,
                    calendarInstance.get(Calendar.YEAR),
                    calendarInstance.get(Calendar.MONTH),
                    calendarInstance.get(Calendar.DAY_OF_MONTH));

            datePickerDialog.show();
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int chosenYear, int chosenMonth, int chosenDay) {
                calendarInstance.set(Calendar.YEAR, chosenYear);
                calendarInstance.set(Calendar.MONTH, chosenMonth);
                calendarInstance.set(Calendar.DAY_OF_MONTH, chosenDay);
                setChosenDateInDateField();
            }
        };

    }
    private void setChosenDateInDateField() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        dob.setText(sdf.format(calendarInstance.getTime()));

        userDOB = calendarInstance.getTime().toString();
    }

    private void setSponsorClickListener() {
        sponsor.setOnClickListener(view ->
                NavigationController.showCityListScreen(ProfileActivity.this,
                        Constants.CALLER_SPONSOR));
    }

    private void setCityClickListener() {
        city.setOnClickListener(view ->
                NavigationController.showCityListScreen(ProfileActivity.this,
                        Constants.CALLER_CITY));
    }
}
