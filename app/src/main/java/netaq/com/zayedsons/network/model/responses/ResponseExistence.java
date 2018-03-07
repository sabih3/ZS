package netaq.com.zayedsons.network.model.responses;

import com.google.gson.annotations.SerializedName;
import netaq.com.zayedsons.model.AccountInfo;
import netaq.com.zayedsons.model.Profile;

/**
 * Created by sabih on 05-Mar-18.
 */

public class ResponseExistence extends BaseResponse {

    @SerializedName("AccountInfo")
    public AccountInfo accountInfo;
    @SerializedName("Profile")
    public Profile profile;
}
