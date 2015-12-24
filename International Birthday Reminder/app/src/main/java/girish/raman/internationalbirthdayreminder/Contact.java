package girish.raman.internationalbirthdayreminder;

public class Contact {

    String name;
    String birthday;
    String ID;
    boolean reminder;
    String timeZone;

    Contact(String name, String birthday, String ID, boolean reminder, String timeZone) {
        this.name = name;
        this.birthday = birthday;
        this.ID = ID;
        this.reminder = reminder;
        this.timeZone= timeZone;
    }
}