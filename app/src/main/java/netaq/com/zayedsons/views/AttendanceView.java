package netaq.com.zayedsons.views;

import netaq.com.zayedsons.network.model.responses.ResponseAttendeeInfo;

/**
 * Created by sabih on 28-Mar-18.
 */

public interface AttendanceView extends BaseView {

    void onAttendanceInfoReceived(ResponseAttendeeInfo attendanceInfo);
}
