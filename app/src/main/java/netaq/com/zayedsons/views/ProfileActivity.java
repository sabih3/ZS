package netaq.com.zayedsons.views;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import netaq.com.zayedsons.R;
import netaq.com.zayedsons.model.Profile;
import netaq.com.zayedsons.network.model.responses.ResponseRegister;
import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by M.Refaat on 3/14/2018.
 */

public class ProfileActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.exit)
    ImageButton exit;
    @BindView(R.id.profile_photo)
    CircleImageView profilePhoto;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.profile_email)
    EditText email;
    @BindView(R.id.profile_mobile)
    EditText mobile;
    @BindView(R.id.profile_name)
    EditText fullName;
    @BindView(R.id.profile_dob)
    EditText dob;
    @BindView(R.id.profile_city)
    EditText city;
    @BindView(R.id.profile_university)
    EditText university;
    @BindView(R.id.profile_major)
    EditText major;
    @BindView(R.id.profile_sponsor)
    EditText sponsor;

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
        exit.setOnClickListener(this);
    }

    /**
     * To set the user profile credentials
     */
    private void setProfileCredentials() {
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
        // assign it to the fields
        String name1 = userProfile.getName1();
        String name2 = userProfile.getName2();
        if (name1 == null && name2 == null)
            setText(name, null);
        else
            setText(name, (name1 == null ? "" : name1) + " " + (name2 == null ? "" : name2));
        setText(email, user.getAccountInfo().getEmail());
        setText(mobile, user.getAccountInfo().getMobileNo());
        setText(fullName, user.getAccountInfo().getFullName());
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
        if (value == null) {
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

    @Override
    public void onClick(View view) {
        if (view == exit) {
            finish();
        }
    }
}
