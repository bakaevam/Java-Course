public class Request {
    Alarm alarm;
    AlarmState state;
    int idxDelete;
    boolean deleteFromDB;

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public void setState(AlarmState state) {
        this.state = state;
    }

    public void setIdxDelete(int idxDelete) {
        this.idxDelete = idxDelete;
    }

    public void setDeleteFromDB(boolean deleteFromDB) {
        this.deleteFromDB = deleteFromDB;
    }
}