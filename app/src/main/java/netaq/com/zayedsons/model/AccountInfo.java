package netaq.com.zayedsons.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sabih on 05-Mar-18.
 */

public class AccountInfo implements Serializable{
    @SerializedName("UserID")
    private String userID;
    @SerializedName("UserName")
    private String userName;
    @SerializedName("Password")
    private String password;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("Gender")
    private int gender;
    @SerializedName("GenderCode")
    private String genderCode;
    @SerializedName("Email")
    private String email;
    @SerializedName("MobileNo")
    private String mobileNo;
    @SerializedName("IsApproved")
    private boolean isApproved;
    @SerializedName("UserTypeID")
    private String userTypeID;
    @SerializedName("UserType")
    private int userType;
    @SerializedName("StrUserType")
    private String strUserType;

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

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setGenderCode(String genderCode) {
        this.genderCode = genderCode;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public void setUserTypeID(String userTypeID) {
        this.userTypeID = userTypeID;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public void setStrUserType(String strUserType) {
        this.strUserType = strUserType;
    }
}
