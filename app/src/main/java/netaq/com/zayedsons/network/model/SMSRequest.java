package netaq.com.zayedsons.network.model;


/**
 * Created by Sabih Ahmed on 04/03/2018.
 */

public class SMSRequest {

    public String content;
    public String[] to;

    public SMSRequest(String text, String[] to) {
        this.content = text;
        this.to = to;
    }



}
