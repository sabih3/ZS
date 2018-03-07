package netaq.com.zayedsons.network.model.responses;

import java.io.Serializable;

import netaq.com.zayedsons.model.Profile;
import netaq.com.zayedsons.model.AccountInfo;

/**
 * Created by sabih on 06-Mar-18.
 */

public class ResponseRegister extends BaseResponse implements Serializable{

    private String AuthToken;
    private AccountInfo AccountInfo;
    private Profile Profile;

    public String getAuthToken() {
        return AuthToken;
    }

    public netaq.com.zayedsons.model.AccountInfo getAccountInfo() {
        return AccountInfo;
    }

    public Profile getProfile() {
        return Profile;
    }
}
