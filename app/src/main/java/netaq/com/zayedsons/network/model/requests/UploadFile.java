package netaq.com.zayedsons.network.model.requests;

import netaq.com.zayedsons.network.model.BaseModel;

/**
 * Created by sabih on 01-Mar-18.
 */

public class UploadFile extends BaseModel {

    private String ForID ;
    private String RefID ;
    private String FileBase64;
    private String FileName;

    public void setForID(String forID) {
        ForID = forID;
    }

    public void setRefID(String refID) {
        RefID = refID;
    }

    public void setFileBase64(String fileBase64) {
        FileBase64 = fileBase64;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
