package netaq.com.zayedsons.utils;

import java.util.ArrayList;
import java.util.List;

import netaq.com.zayedsons.network.Constants;

/**
 * Created by sabih on 26-Feb-18.
 */

public class AppConfig {

    public static List<String> getDrawerItems(){
        List<String> drawerItems = new ArrayList<>();
        boolean userIsOrganizer;

        if(UserManager.getUser().getAccountInfo().getUserTypeID().equals(Constants.USER_TYPE_POWER)){
            drawerItems.add("Profile");
            drawerItems.add("Events");
            drawerItems.add("Attendance");
            drawerItems.add("Log out");

        }else{
            drawerItems.add("Profile");
            drawerItems.add("Events");
            drawerItems.add("Log out");
        }


        return drawerItems;
    }
}
