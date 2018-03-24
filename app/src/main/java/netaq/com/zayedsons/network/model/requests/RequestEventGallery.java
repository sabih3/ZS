package netaq.com.zayedsons.network.model.requests;

import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 24-Mar-18.
 */

public class RequestEventGallery extends BaseModel{

    private String token;

    private String usr;

    private String refID;

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }



    public void setRefID(String refID) {
        this.refID = refID;
    }
}
