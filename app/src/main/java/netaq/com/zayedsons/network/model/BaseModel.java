package netaq.com.zayedsons.network.model;

import java.io.Serializable;

/**
 * Created by sabih on 01-Mar-18.
 */

public class BaseModel implements Serializable{

    private String Lang;

    public void setLang(String lang) {
        Lang = lang;
    }

    private String DeviceID;

    private String DeviceName;

    private String DeviceDetail;

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public void setDeviceName(String deviceName) {
        DeviceName = deviceName;
    }

    public void setDeviceDetail(String deviceDetail) {
        DeviceDetail = deviceDetail;
    }
}
