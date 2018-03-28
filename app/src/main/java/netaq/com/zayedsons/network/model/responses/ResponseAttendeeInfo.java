package netaq.com.zayedsons.network.model.responses;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sabih on 27-Mar-18.
 */

public class ResponseAttendeeInfo extends BaseResponse{

    @SerializedName("ID")
    private String eventID;

    private String EventTitle;

    private String FullName;

    private String ProfilePic;

    private int RegStatusID;

    private String RegStatus;

    private String eventDay;

    private String eventTime;

    public String getEventID() {
        return eventID;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public String getFullName() {
        return FullName;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public int getRegStatusID() {
        return RegStatusID;
    }

    public String getRegStatus() {
        return RegStatus;
    }

    public void setEventDay(String eventDay) {
        this.eventDay = eventDay;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }


    public String getEventDay() {
        return eventDay;
    }

    public String getEventTime() {
        return eventTime;
    }
}
