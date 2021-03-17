package clocks;

public interface IAlarm {
    boolean ringOut();
    void setAlarm(int h, int m, int s);
}
