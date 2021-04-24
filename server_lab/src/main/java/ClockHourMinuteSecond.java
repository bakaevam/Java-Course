import java.util.ArrayList;
import java.util.Objects;

public class ClockHourMinuteSecond extends ClockHourMinute {
    int second = 0;
    ArrayList<Alarm> alarms = new ArrayList<Alarm>();

    @Override
    public String toString() {
        return hour + ":" + minute + ":" + second;
    }

    @Override
    public int getSecond() {
        return second;
    }

    @Override
    public void start() {
        if (t == null) {
            t = new Thread() {
                @Override
                public void run() {
                    f1 = true;

                    while (f1) {
                        try {
                            try {
                                changeTime(0, 0, 1);
                            } catch (Exception ex) {
                                ex.fillInStackTrace();
                            }
                            sleep(1000);
                           // System.out.println(hour + ":" + minute + ":" + second);
                        } catch (InterruptedException ex) {
                            f1 = false;
                        }
                    }
                }
            };
        }

        t.start();
    }

    public void stop() {
        if (t != null) {
            t.interrupt();
            t = null;
        }
    }

    @Override
    public void startClock() {
        start();
    }

    @Override
    public void setSecond(int second) throws Exception {
        if (second < 60 && second >= 0) {
            this.second = second;
        } else {
            throw new Exception("Incorrect second");
        }
    }

    @Override
    public void changeTime(int hour, int minute, int second) throws Exception {
        if (hour < 0 || minute < 0 || second < 0) {
            throw new Exception("Incorrect time");
        }

        int deltaHour = 0;
        int deltaMinute = 0;

        if (this.second + second >= 60) {
            deltaMinute = (this.second + second) / 60;
            setSecond((this.second + second) - ((this.second + second) / 60) * 60);
        } else {
            this.second += second;
        }

        minute += deltaMinute;
        if (this.minute + minute >= 60) {
            deltaHour = (this.minute + minute) / 60;
            setMinute((this.minute + minute) - ((this.minute + minute) / 60) * 60);
        } else {
            this.minute += minute;
        }

        hour += deltaHour;
        if (this.hour + hour >= 24) {
            setHour((this.hour + hour) - ((this.hour + hour) / 24) * 24);
        } else {
            this.hour += hour;
        }
    }

    public void setAlarm(Alarm alarm) {
        alarms.add(alarm);
    }

    public void deleteAlarm(Alarm alarm) {
        System.out.println(alarms);
        System.out.println("IDX " + alarms.indexOf(alarm));
        if (!alarms.contains(alarm)) return;
        alarms.remove(alarm);
        System.out.println(alarms);
    }

    public boolean checkAlarms() {
        boolean flag = false;
        Alarm tmp = null;

        for (Alarm alarm : alarms) {
            if(alarm.check(this)) {
                flag = true;
                tmp = alarm;
            }
        }
        if (flag)
            alarms.remove(tmp);

        return flag;
    }
}
