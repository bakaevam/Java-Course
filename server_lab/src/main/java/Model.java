import java.util.ArrayList;

public class Model {
    ArrayList<Alarm> allAlarms = new ArrayList<Alarm>();
    ArrayList<IObserver> allObservers = new ArrayList<IObserver>();
    String time = "time";
    boolean alarmFlag = false;

    public void addAlarm(Alarm alarm) {
        allAlarms.add(alarm);
        update();
    }

    public void addTime(String string, boolean flag) {
        time = string;
        alarmFlag = flag;
        updateTime();
    }

    public ArrayList<Alarm> getAllAlarms() {
        return allAlarms;
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
        return allAlarms.get(allAlarms.size() - 1);
    }

    public void addObserver(IObserver observer) {
        allObservers.add(observer);
    }
}
