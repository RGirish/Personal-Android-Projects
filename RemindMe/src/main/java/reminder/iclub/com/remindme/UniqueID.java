package reminder.iclub.com.remindme;

public class UniqueID {
    static long current= System.currentTimeMillis();
    static public synchronized long get(){
        return current++;
    }
}