package netaq.com.zayedsons.model;

import java.util.List;

import netaq.com.zayedsons.network.model.responses.BaseResponse;

/**
 * Created by sabih on 06-Mar-18.
 */

public class Lookup extends BaseResponse {

    private List<Lookups> Lookups;

    public class Lookups {

        private String ID;
        private String IntID;
        private String Code;
        private String Title;
        private String GroupName;


        public String getID() {
            return ID;
        }

        public String getIntID() {
            return IntID;
        }

        public String getCode() {
            return Code;
        }

        public String getTitle() {
            return Title;
        }

        public String getGroupName() {
            return GroupName;
        }

        @Override
        public String toString() {
            return Title;
        }
    }


    public List<Lookup.Lookups> getLookups() {
        return Lookups;
    }
}
