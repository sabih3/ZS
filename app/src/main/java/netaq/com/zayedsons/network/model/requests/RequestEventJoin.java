package netaq.com.zayedsons.network.model.requests;

import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 13-Mar-18.
 */

public class RequestEventJoin extends BaseModel{

    private String token;
    private String usr;
    private String id;


    public void setToken(String token) {
        this.token = token;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }


    public void setId(String id) {
        this.id = id;
    }
}
