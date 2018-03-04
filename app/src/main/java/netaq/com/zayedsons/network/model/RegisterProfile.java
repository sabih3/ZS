package netaq.com.zayedsons.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by sabih on 04-Mar-18.
 */

public class RegisterProfile extends BaseModel{


    public AccountInfo accountInfo;

    public Profile profile;

    private class AccountInfo {
        @SerializedName("UserID")
        public String userID;
        @SerializedName("UserName")
        public String userName;
        @SerializedName("Password")
        public String password;
        @SerializedName("FullName")
        public String fullName;
        @SerializedName("MobileNo")
        public String mobileNo;
        @SerializedName("Gender")
        public String gender;
        @SerializedName("GenderCode")
        public String genderCode;
        @SerializedName("Email")
        public String email;
        @SerializedName("IsApproved")
        public boolean isApproved;
        @SerializedName("UserTypeID")
        public String userTypeID;
        @SerializedName("StrUserType")
        public String strUserType;


        public void setUserID(String userID) {
            this.userID = userID;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public void setMobileNo(String mobileNo) {
            this.mobileNo = mobileNo;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public void setGenderCode(String genderCode) {
            this.genderCode = genderCode;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setApproved(boolean approved) {
            isApproved = approved;
        }

        public void setUserTypeID(String userTypeID) {
            this.userTypeID = userTypeID;
        }

        public void setStrUserType(String strUserType) {
            this.strUserType = strUserType;
        }
    }


    private class Profile {
        @SerializedName("Name1")
        public String name1;
        @SerializedName("Name2")
        public String name2;
        @SerializedName("Name3")
        public String name3;
        @SerializedName("Name4")
        public String name4;
        @SerializedName("ProfilePic")
        public String profilePic;
        @SerializedName("DOB")
        public Date dOB;
        @SerializedName("EIDNo")
        public String eIDNo;
        @SerializedName("CityID")
        public String cityID;
        @SerializedName("University")
        public String university;
        @SerializedName("CourseMajor")
        public String courseMajor;
        @SerializedName("SponsorID")
        public String sponsorID;


        public void setName1(String name1) {
            this.name1 = name1;
        }

        public void setName2(String name2) {
            this.name2 = name2;
        }

        public void setName3(String name3) {
            this.name3 = name3;
        }

        public void setName4(String name4) {
            this.name4 = name4;
        }

        public void setProfilePic(String profilePic) {
            this.profilePic = profilePic;
        }

        public void setdOB(Date dOB) {
            this.dOB = dOB;
        }

        public void seteIDNo(String eIDNo) {
            this.eIDNo = eIDNo;
        }

        public void setCityID(String cityID) {
            this.cityID = cityID;
        }

        public void setUniversity(String university) {
            this.university = university;
        }

        public void setCourseMajor(String courseMajor) {
            this.courseMajor = courseMajor;
        }

        public void setSponsorID(String sponsorID) {
            this.sponsorID = sponsorID;
        }
    }


}
