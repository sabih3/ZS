package netaq.com.zayedsons.network.model.requests;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sabih on 14-Mar-18.
 */

public class RequestQR {

    private String token;
    private String usr;
    private String devID;

    @SerializedName("id")
    private String registrationID;

    private String day;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public void setRegistrationID(String registrationID) {
        this.registrationID = registrationID;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
