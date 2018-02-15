package netaq.com.zayedsons.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by sabih on 12-Feb-18.
 */

public class EventList {

    public List<Event> getEvents() {
        return events;
    }

    public List<Event> events;
    

    private class Event {
        @SerializedName("CreatedByID")
        public String createdByID;
        @SerializedName("CreatedAt")
        public String createdAt;
        @SerializedName("Sort")
        public String sort;
        @SerializedName("Active")
        public String active;
        @SerializedName("ImageURL")
        public String imageURL;
        @SerializedName("Title")
        public String title;
        @SerializedName("Details")
        public String details;
        @SerializedName("StartDate")
        public String startDate;
        @SerializedName("EndDate")
        public String endDate;
        @SerializedName("Venue")
        public String venue;
        @SerializedName("Address")
        public String address;
        @SerializedName("ContactNo")
        public String contactNo;
        @SerializedName("Email")
        public String email;
        @SerializedName("CountryID")
        public String countryID;
        @SerializedName("ID")
        public String iD;
        @SerializedName("Map_Lat")
        public String map_Lat;
        @SerializedName("Map_Lng")
        public String map_Lng;
        @SerializedName("Map_Zoom")
        public String map_Zoom;
        @SerializedName("IsDeleted")
        public String isDeleted;
        @SerializedName("ActivityByID")
        public String activityByID;
        @SerializedName("ActivityAt")
        public String activityAt;
        @SerializedName("EVENTSSESSIONS")
        public List<EventSessions> eventSessions;


        public String getCreatedByID() {
            return createdByID;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public String getSort() {
            return sort;
        }

        public String getActive() {
            return active;
        }

        public String getImageURL() {
            return imageURL;
        }

        public String getTitle() {
            return title;
        }

        public String getDetails() {
            return details;
        }

        public String getStartDate() {
            return startDate;
        }

        public String getEndDate() {
            return endDate;
        }

        public String getVenue() {
            return venue;
        }

        public String getAddress() {
            return address;
        }

        public String getContactNo() {
            return contactNo;
        }

        public String getEmail() {
            return email;
        }

        public String getCountryID() {
            return countryID;
        }

        public String getiD() {
            return iD;
        }

        public String getMap_Lat() {
            return map_Lat;
        }

        public String getMap_Lng() {
            return map_Lng;
        }

        public String getMap_Zoom() {
            return map_Zoom;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        public String getActivityByID() {
            return activityByID;
        }

        public String getActivityAt() {
            return activityAt;
        }

        public List<EventSessions> getEventSessions() {
            return eventSessions;
        }

        public class EventSessions {
            @SerializedName("ID")
            public String iD;
            @SerializedName("Sort")
            public String sort;
            @SerializedName("Title")
            public String title;
            @SerializedName("Details")
            public String details;
            @SerializedName("StartTime")
            public String startTime;
            @SerializedName("EndTime")
            public String endTime;
            @SerializedName("SpeakerName")
            public String speakerName;
            @SerializedName("SpeakerDesignation")
            public String speakerDesignation;
            @SerializedName("SpeakerImage")
            public String speakerImage;
            @SerializedName("EventID")
            public String eventID;


            public String getiD() {
                return iD;
            }

            public String getSort() {
                return sort;
            }

            public String getTitle() {
                return title;
            }

            public String getDetails() {
                return details;
            }

            public String getStartTime() {
                return startTime;
            }

            public String getEndTime() {
                return endTime;
            }

            public String getSpeakerName() {
                return speakerName;
            }

            public String getSpeakerDesignation() {
                return speakerDesignation;
            }

            public String getSpeakerImage() {
                return speakerImage;
            }

            public String getEventID() {
                return eventID;
            }
        }
    }
}
