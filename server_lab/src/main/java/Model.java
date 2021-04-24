import java.sql.*;
import java.util.ArrayList;

public class Model {
    ArrayList<Alarm> allAlarms;
    ArrayList<IObserver> allObservers = new ArrayList<IObserver>();
    String time = "9:10:59";
    boolean alarmFlag = false;
    int idxDelete;

    DBHandler dbHandler = DBHandler.getInstance();

    public void addAlarm(Alarm alarm) {
        //allAlarms.add(alarm);
        dbHandler.saveAlarmToDB(alarm);
        update();
    }

    public void deleteAlarm(Alarm alarm, int idx) {
        dbHandler.deleteAlarmFromDB(alarm);
        idxDelete = idx;
        updateDelete();
    }

    public void deleteAlarmAfterRinging(Alarm alarm, int idx) {
        dbHandler.deleteAlarmFromDB(alarm);
        idxDelete = idx;
        updateDeleteAfterRinging();
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

    public void updateDelete() {
        for (IObserver observer : allObservers) {
            observer.updateAfterDelete(this);
        }
    }

    public void updateDeleteAfterRinging() {
        for (IObserver observer : allObservers) {
            observer.updateAfterRinging(this);
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
