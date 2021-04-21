import java.sql.*;
import java.util.ArrayList;

public class Model {
    ArrayList<Alarm> allAlarms;
    ArrayList<IObserver> allObservers = new ArrayList<IObserver>();
    String time = "time";
    boolean alarmFlag = false;

    DBHandler dbHandler = DBHandler.getInstance();

    public void addAlarm(Alarm alarm) {
        //allAlarms.add(alarm);
        dbHandler.saveAlarmToDB(alarm);
        update();
    }

    public void deleteAlarm(Alarm alarm) {
        dbHandler.deleteAlarmFromDB(alarm);
        update();
    }

    public void addTime(String string, boolean flag) {
        time = string;
        alarmFlag = flag;
        updateTime();
    }

    public ArrayList<Alarm> getAllAlarms() {
        return dbHandler.getListFromDB();
    }

    public void update() {
        for (IObserver observer : allObservers) {
            observer.update(this);
        }
    }

    public void updateTime() {
        for (IObserver observer : allObservers) {
            observer.updateTime(this);
        }
    }

    public Alarm last() {
        allAlarms = getAllAlarms();
        return allAlarms.get(allAlarms.size() - 1);
    }

    public void addObserver(IObserver observer) {
        allObservers.add(observer);
    }
}
