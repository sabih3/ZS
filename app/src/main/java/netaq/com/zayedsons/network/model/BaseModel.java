package netaq.com.zayedsons.network.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import netaq.com.zayedsons.utils.UserManager;

/**
 * Created by sabih on 01-Mar-18.
 */

public class BaseModel implements Serializable{

    private String Lang = "en-US";

    public void setLang(String lang) {
        Lang = lang;
    }

    private String token;

    private String usr;

    @SerializedName("devID")
    private String DeviceID = UserManager.getDeviceID();

    @SerializedName("devName")
    private String DeviceName;

    @SerializedName("devDetail")
    private String DeviceDetail;

    private String devType = "Android";

    private String pushID = UserManager.getPushToken();



    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public void setDeviceDetail(String deviceDetail) {
        DeviceDetail = deviceDetail;
    }

    public void setPushID(String pushID) {
        this.pushID = pushID;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public void setDevType(String devType) {
        this.devType = devType;
    }
}
