package clocks;

public class ClockHourMinuteSecond extends ClockHourMinute{
    int second = 0;
    Alarm alarm;
    boolean flag = false;

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
                            System.out.println(hour + ":" + minute + ":" + second);
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
    public String toString() {
        return "ClockHourMinuteSecond{" +
                "hour = " + hour + ", minute = " + minute +
                ", second = " + second + '}';
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

    void setAlarm(Alarm alarm) {
        this.alarm = alarm;
        flag = true;
    }

    boolean ringOut() {
        if(flag) {
            if (alarm.hh == hour && alarm.mm == minute && alarm.ss == second) {
                return true;
            }
            return false;
        }
        return false;
    }
}
