package netaq.com.zayedsons.network.model;

import java.util.ArrayList;

/**
 * Created by Sabih Ahmed on 04/03/2018.
 */

public class ResponseSMS {

    public ArrayList<Message> messages;
    public String error;
    public ArrayList<Message> getMessage() {
        return messages;
    }

    private class Message {

        public boolean accepted;
        public String to;
        public String apiMessageId;


        public boolean isAccepted() {
            return accepted;
        }

        public String getTo() {
            return to;
        }

        public String getApiMessageId() {
            return apiMessageId;
        }
    }

}
