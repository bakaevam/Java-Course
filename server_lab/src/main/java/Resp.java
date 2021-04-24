import java.util.ArrayList;

public class Resp {
    ArrayList<String> listAlarms = new ArrayList<String>();
    String time = "time";
    boolean alarmFlag;
    Alarm newAlarm;
    boolean idNeededDelete;
    boolean isNeededCreate;
    Alarm deleteAlarm;
    Alarm createAlarm;
    int idxDeleteAlarm;

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

    public void setIdNeededDelete(boolean idNeededDelete) {
        this.idNeededDelete = idNeededDelete;
    }

    public void setDeleteAlarm(Alarm deleteAlarm) {
        this.deleteAlarm = deleteAlarm;
    }

    public void setCreateAlarm(Alarm createAlarm) {
        this.createAlarm = createAlarm;
    }

    public void setNeededCreate(boolean neededCreate) {
        isNeededCreate = neededCreate;
    }

    public void setIdxDeleteAlarm(int idxDeleteAlarm) {
        this.idxDeleteAlarm = idxDeleteAlarm;
    }
}
