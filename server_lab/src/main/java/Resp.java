import java.util.ArrayList;

public class Resp {
    ArrayList<String> listAlarms = new ArrayList<String>();
    String time = "time";
    boolean alarmFlag;
    Alarm newAlarm;

    public void addAlarm(String alarm) {
        listAlarms.add(alarm);
    }

    public ArrayList<String> getListAlarms() {
        return listAlarms;
    }

    public void setNewTime(String time) {
        this.time = time;
    }

    public void setAlarmFlag(boolean flag) {
        alarmFlag = flag;
    }
}
