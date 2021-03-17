package clocks;

public class Alarm implements IAlarm {
    int ss = 0, mm = 0, hh = 0;

    Alarm(int s, int m, int h) {
        ss = s;
        mm = m;
        hh = h;
    }

    @Override
    public boolean ringOut() {
        return false;
    }

    @Override
    public void setAlarm(int h, int m, int s) {
        ss = s;
        hh = h;
        mm = m;
    }
}
