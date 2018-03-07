package netaq.com.zayedsons.network.model.requests;

import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 04-Mar-18.
 */

public class RequestAccountExistence extends BaseModel {

    public String id;
    public String token;
    public String devID;
    public String devName;
    public String devDetail;


    public void setId(String id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public void setDevDetail(String devDetail) {
        this.devDetail = devDetail;
    }


}
