package netaq.com.zayedsons.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Sabih Ahmed on 12-Mar-18.
 */

public class Event implements Serializable{

    public String ID;

    public String Lang;

    public String ImageURL;

    public String Title;

    public String Details;

    public String StartDate;

    public String EndDate;

    public String Venue;

    public String Address;

    public String ContactName;

    public String ContactNo;

    public String ContactEmail;

    public String CountryID;

    public String CtrShortName;

    public String CountryName;

    public double Map_Lat;

    public double Map_Lng;

    public int Map_Zoom;

    public String QRUrl;

    public boolean Registered;

    public String RegID;

    public boolean RegApproved;

    public String RegStatus;

    public List<EventDay> EventDays;

    public List<Session> Sessions;

    @SerializedName("IsPast")
    private boolean isArchived ;

    public String getID() {
        return ID;
    }

    public String getLang() {
        return Lang;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public String getTitle() {
        return Title;
    }

    public String getDetails() {
        return Details;
    }

    public String getStartDate() {
        return StartDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public String getVenue() {
        return Venue;
    }

    public String getAddress() {
        return Address;
    }

    public String getContactName() {
        return ContactName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public String getContactEmail() {
        return ContactEmail;
    }

    public String getCountryID() {
        return CountryID;
    }

    public String getCtrShortName() {
        return CtrShortName;
    }

    public String getCountryName() {
        return CountryName;
    }

    public double getMap_Lat() {
        return Map_Lat;
    }

    public double getMap_Lng() {
        return Map_Lng;
    }

    public int getMap_Zoom() {
        return Map_Zoom;
    }

    public String getQRUrl() {
        return QRUrl;
    }

    public boolean isRegistered() {
        return Registered;
    }

    public String getRegID() {
        return RegID;
    }

    public boolean isRegApproved() {
        return RegApproved;
    }

    public String getRegStatus() {
        return RegStatus;
    }

    public List<EventDay> getEventDays() {
        return EventDays;
    }

    public List<Session> getSessions() {
        return Sessions;
    }

    public void setArchived(boolean isArchived) {
        this.isArchived = isArchived;
    }

    public boolean isArchived() {
        return this.isArchived;
    }

    public class EventDay implements Serializable{
        public String Day;
        public boolean Attended;

        public String getDay() {
            return Day;
        }

        public boolean isAttended() {
            return Attended;
        }
    }

    private class Session implements Serializable{
        public String ID;

        public String Title;

        public String Details;

        public String StartTime;

        public String EndTime;

        public String SpeakerName;

        public String SpeakerTitle;

        public String SpeakerImageURL;

        public String getID() {
            return ID;
        }

        public String getTitle() {
            return Title;
        }

        public String getDetails() {
            return Details;
        }

        public String getStartTime() {
            return StartTime;
        }

        public String getEndTime() {
            return EndTime;
        }

        public String getSpeakerName() {
            return SpeakerName;
        }

        public String getSpeakerTitle() {
            return SpeakerTitle;
        }

        public String getSpeakerImageURL() {
            return SpeakerImageURL;
        }
    }
}
