import java.util.Objects;

public class Alarm {
    int ss = 0, mm = 0, hh = 0;

    Alarm(int s, int m, int h) {
        ss = s;
        mm = m;
        hh = h;
    }

    public void setAlarm(int h, int m, int s) {
        ss = s;
        hh = h;
        mm = m;
    }

    public boolean check(ClockHourMinuteSecond clock) {
        return (hh == clock.hour && mm == clock.minute && ss == clock.second);
    }

    @Override
    public String toString() {
        return hh + ":" + mm + ":" + ss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alarm alarm = (Alarm) o;
        return ss == alarm.ss && mm == alarm.mm && hh == alarm.hh;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ss, mm, hh);
    }
}
