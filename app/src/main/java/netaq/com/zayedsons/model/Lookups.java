package netaq.com.zayedsons.model;

/**
 * Created by sabih on 09-Apr-18.
 */
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

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setIntID(String intID) {
        IntID = intID;
    }

    public void setCode(String code) {
        Code = code;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setGroupName(String groupName) {
        GroupName = groupName;
    }
}
