package netaq.com.zayedsons.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabih on 26-Feb-18.
 */

public class AppConfig {

    public static List<String> getDrawerItems(){

        List<String> drawerItems = new ArrayList<>();
        drawerItems.add("Profile");
        drawerItems.add("Events");
        drawerItems.add("Attendance");
        drawerItems.add("Log out");
        return drawerItems;
    }
}
