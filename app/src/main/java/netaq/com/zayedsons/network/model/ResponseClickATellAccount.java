package netaq.com.zayedsons.network.model;

import java.util.List;

/**
 * Created by sabih on 04-Mar-18.
 */

public class ResponseClickATellAccount {

    public Data data;

    public class Data {

        public List<Message> message;

        public List<Message> getMessage() {
            return message;
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
}
