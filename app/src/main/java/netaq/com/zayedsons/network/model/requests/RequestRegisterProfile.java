package netaq.com.zayedsons.network.model.requests;

import java.io.Serializable;

import netaq.com.zayedsons.model.AccountInfo;
import netaq.com.zayedsons.model.Profile;
import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 04-Mar-18.
 */

public class RequestRegisterProfile extends BaseModel implements Serializable{


    public AccountInfo accountInfo;

    public Profile profile;

    public void setAccountInfo(AccountInfo accountInfo) {
        this.accountInfo = accountInfo;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
