package netaq.com.zayedsons.network.model.requests;

/**
 * Created by sabih on 13-Mar-18.
 */

public class RequestEventJoin {

    private String token;
    private String usr;
    private String devID;
    private String id;


    public void setToken(String token) {
        this.token = token;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setDevID(String devID) {
        this.devID = devID;
    }

    public void setId(String id) {
        this.id = id;
    }
}
