package netaq.com.zayedsons.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sabih on 05-Mar-18.
 */

public class Profile implements Serializable{
    @SerializedName("ID")
    private String iD;
    @SerializedName("CreatedAt")
    private String createdAt;
    @SerializedName("Name1")
    private String name1;
    @SerializedName("Name2")
    private String name2;
    @SerializedName("Name3")
    private String name3;
    @SerializedName("Name4")
    private String name4;
    @SerializedName("FullName")
    private String fullName;
    @SerializedName("ProfilePic")
    private String profilePic;
    @SerializedName("DOB")
    private String dOB;
    @SerializedName("EIDNo")
    private String eIDNo;
    @SerializedName("CityID")
    private String cityID;
    @SerializedName("ShortCode")
    private String shortCode;
    @SerializedName("CityName")
    private String cityName;
    @SerializedName("StateShortCode")
    private String stateShortCode;
    @SerializedName("StateName")
    private String stateName;
    @SerializedName("CtrShortName")
    private String ctrShortName;
    @SerializedName("CountryName")
    private String countryName;
    @SerializedName("University")
    private String university;
    @SerializedName("CourseMajor")
    private String courseMajor;
    @SerializedName("SponsorID")
    private String sponsorID;
    @SerializedName("SponsorIntID")
    private int sponsorIntID;
    @SerializedName("SponsorCode")
    private String sponsorCode;
    @SerializedName("SponsorName")
    private String sponsorName;


    public String getiD() {
        return iD;
    }

    public void setiD(String iD) {
        this.iD = iD;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getName3() {
        return name3;
    }

    public void setName3(String name3) {
        this.name3 = name3;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getdOB() {
        return dOB;
    }

    public void setdOB(String dOB) {
        this.dOB = dOB;
    }

    public String geteIDNo() {
        return eIDNo;
    }

    public void seteIDNo(String eIDNo) {
        this.eIDNo = eIDNo;
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateShortCode() {
        return stateShortCode;
    }

    public void setStateShortCode(String stateShortCode) {
        this.stateShortCode = stateShortCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getCtrShortName() {
        return ctrShortName;
    }

    public void setCtrShortName(String ctrShortName) {
        this.ctrShortName = ctrShortName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public String getCourseMajor() {
        return courseMajor;
    }

    public void setCourseMajor(String courseMajor) {
        this.courseMajor = courseMajor;
    }

    public String getSponsorID() {
        return sponsorID;
    }

    public void setSponsorID(String sponsorID) {
        this.sponsorID = sponsorID;
    }

    public int getSponsorIntID() {
        return sponsorIntID;
    }

    public void setSponsorIntID(int sponsorIntID) {
        this.sponsorIntID = sponsorIntID;
    }

    public String getSponsorCode() {
        return sponsorCode;
    }

    public void setSponsorCode(String sponsorCode) {
        this.sponsorCode = sponsorCode;
    }

    public String getSponsorName() {
        return sponsorName;
    }

    public void setSponsorName(String sponsorName) {
        this.sponsorName = sponsorName;
    }
}
