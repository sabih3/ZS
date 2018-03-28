package netaq.com.zayedsons.network.model.requests;

import com.google.gson.annotations.SerializedName;

import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 27-Mar-18.
 */

public class RequestAttendance extends BaseModel{

    private String token;

    @SerializedName("usr")
    private String usr;

    @SerializedName("id")
    private String registrationID;

    private String ed;

    private String ex;


    public void setToken(String token) {
        this.token = token;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public void setEd(String ed) {
        this.ed = ed;
    }

    public void setEx(String ex) {
        this.ex = ex;
    }
}
