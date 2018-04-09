package netaq.com.zayedsons.model;

import java.util.List;

import netaq.com.zayedsons.network.model.responses.BaseResponse;

/**
 * Created by sabih on 06-Mar-18.
 */

public class Lookup extends BaseResponse {

    private List<Lookups> Lookups;


    public List<netaq.com.zayedsons.model.Lookups> getLookups() {
        return Lookups;
    }
}
