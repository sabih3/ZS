package netaq.com.zayedsons.network.model.requests;

import com.google.gson.annotations.SerializedName;

import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 27-Mar-18.
 */

public class RequestAttendeeInfo extends BaseModel {


    private String token;

    @SerializedName("usr")
    private String userID;

    @SerializedName("id")
    private String registrationID;


    public void setToken(String token) {
        this.token = token;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setEventID(String eventID) {
        this.registrationID = eventID;
    }
}
