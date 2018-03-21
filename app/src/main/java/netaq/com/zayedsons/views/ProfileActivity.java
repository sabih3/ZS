package netaq.com.zayedsons.views;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.core.NavigationController;
import netaq.com.zayedsons.model.AccountInfo;
import netaq.com.zayedsons.model.Profile;
import netaq.com.zayedsons.network.Constants;
import netaq.com.zayedsons.network.model.requests.RequestRegisterProfile;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.UIUtils;
import netaq.com.zayedsons.utils.UserManager;
import netaq.com.zayedsons.utils.Utils;

/**
 * Created by M.Refaat on 3/14/2018.
 */

public class ProfileActivity extends Activity{

    @BindView(R.id.exit) ImageView exit;
    @BindView(R.id.profile_photo) CircleImageView profilePhoto;
    @BindView(R.id.edit_photo)CircleImageView editProfile;

    @BindView(R.id.profile_full_name) TextView name;
    @BindView(R.id.profile_email) EditText email;
    @BindView(R.id.profile_mobile) EditText mobile;

    @BindView(R.id.profile_first_name) EditText firstName;
    @BindView(R.id.father_name)EditText fatherName;
    @BindView(R.id.middle_name)EditText middleName;
    @BindView(R.id.last_name)EditText lastName;

    @BindView(R.id.profile_dob) AppCompatEditText dob;
    @BindView(R.id.profile_city) AppCompatEditText city;
    @BindView(R.id.profile_university) EditText university;
    @BindView(R.id.profile_major) EditText major;
    @BindView(R.id.profile_sponsor) AppCompatEditText sponsor;

    @BindView(R.id.btn_done)ImageView btn_done;

    private Calendar calendarInstance;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private String selectedSponsor = "-1";
    private String selectedCity = "-1";
    private String updatedDate = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // set the activity view
        setContentView(R.layout.activity_profile);
        // Binding the views
        ButterKnife.bind(this);
        // set the values
        setProfileCredentials();
        // set exit click listener
        exit.setOnClickListener(new ExitProfileListener());




        setDoneButtonListener();
        setCityClickListener();
        setSponsorClickListener();
        setDOBClickListener();
        setGenderClickListener();
        setPhotoEditClickListener();

    }

    private void setDoneButtonListener() {
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UIUtils.showMessageDialog(ProfileActivity.this,
                                          "Are you sure to update your profile?",
                                          "Yes, Proceed",
                                          "Not now",
                                            new UIUtils.DialogButtonListener() {
                    @Override
                    public void onPositiveButtonClicked() {
                        AccountInfo accountInfo = new AccountInfo();
                        Profile profile = new Profile();

                        RequestRegisterProfile updateRequest = new RequestRegisterProfile();
                        updateRequest.setToken(UserManager.getUser().getAuthToken());
                        updateRequest.setDeviceID("1234");

                        accountInfo.setUserID(UserManager.getUser().getAccountInfo().getUserID());
                        accountInfo.setUserName(UserManager.getUser().getAccountInfo().getUserName());
                        accountInfo.setGender(Constants.GENDER_MALE);
                        accountInfo.setEmail(UserManager.getUser().getAccountInfo().getEmail());

                        profile.setName1(firstName.getText().toString());
                        profile.setName2(fatherName.getText().toString());
                        profile.setName3(middleName.getText().toString());
                        profile.setName4(lastName.getText().toString());
                        profile.setdOB(updatedDate);
                        profile.seteIDNo("");
                        profile.setCityID(selectedCity);
                        profile.setUniversity(university.getText().toString());
                        profile.setCourseMajor(major.getText().toString());
                        profile.setSponsorID(selectedSponsor);



                    }

                    @Override
                    public void onNegativeButtonClicked() {
                        resetEditMode();
                    }
                });
            }
        });
    }



    private void setPhotoEditClickListener() {

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

        updatedDate = calendarInstance.getTime().toString();
    }

    private void setSponsorClickListener() {
        sponsor.setOnClickListener(view1 ->
                NavigationController.showCityListScreen(ProfileActivity.this,
                        Constants.CALLER_SPONSOR));
    }

    private void setCityClickListener() {
        city.setOnClickListener(view12 ->
                NavigationController.showCityListScreen(ProfileActivity.this,
                        Constants.CALLER_CITY));
    }

    /**
     * To set the user profile credentials
     */
    private void setProfileCredentials() {

        selectedSponsor = UserManager.getUser().getProfile().getSponsorID();
        selectedCity = UserManager.getUser().getProfile().getCityID();
        updatedDate = UserManager.getUser().getProfile().getdOB();

        // get the user data the DataBase
        ResponseRegister user = UserManager.getUser();
        Profile userProfile = user.getProfile();
        if (userProfile == null) {
            userProfile = new Profile();
        }
        // load the profile photo
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Picasso.with(this).load(userProfile.getProfilePic()).error(getDrawable(R.drawable.ic_person_white_24px)).placeholder(getDrawable(R.drawable.ic_person_white_24px)).into(profilePhoto);
            } else {
                Picasso.with(this).load(userProfile.getProfilePic()).into(profilePhoto);
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }

        setText(name,user.getAccountInfo().getFullName());
        setText(email, user.getAccountInfo().getEmail());
        setText(mobile, user.getAccountInfo().getMobileNo());

        setText(firstName, userProfile.getName1());
        setText(fatherName,userProfile.getName2());
        setText(middleName,userProfile.getName3());
        setText(lastName,userProfile.getName4());

        setText(dob, userProfile.getdOB());
        setText(city, userProfile.getCityName());
        setText(university, userProfile.getUniversity());
        setText(major, userProfile.getCourseMajor());
        setText(sponsor, userProfile.getSponsorName());
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
        exit.setVisibility(View.VISIBLE);
        btn_done.setVisibility(View.GONE);
        editProfile.setVisibility(View.GONE);

        email.setEnabled(false);

        firstName.setEnabled(false);

        middleName.setEnabled(false);

        lastName.setEnabled(false);

        fatherName.setEnabled(false);

        university.setEnabled(false);

        major.setEnabled(false);

        city.setEnabled(false);

        sponsor.setEnabled(false);

        dob.setEnabled(false);
    }

    private class ExitProfileListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            exit.setVisibility(View.GONE);
            btn_done.setVisibility(View.VISIBLE);
            editProfile.setVisibility(View.VISIBLE);

            email.requestFocus();

            email.setEnabled(true);

            firstName.setEnabled(true);

            middleName.setEnabled(true);

            lastName.setEnabled(true);

            fatherName.setEnabled(true);



            university.setEnabled(true);

            major.setEnabled(true);

            city.setEnabled(true);

            sponsor.setEnabled(true);





            dob.setEnabled(true);

        }
    }
}
