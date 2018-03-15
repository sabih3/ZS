package netaq.com.zayedsons.network.model.responses;

import java.io.Serializable;

/**
 * Created by sabih on 01-Mar-18.
 */

public class BaseResponse implements Serializable{

    private String Lang;
    private boolean Success;
    private int StatusCode = -1 ;
    private String StatusText;


    public boolean isSuccess() {
        return Success;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public String getStatusText() {
        return StatusText;
    }

    public String getLang() {
        return Lang;
    }
}
