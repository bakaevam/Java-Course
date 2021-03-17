package clocks;

public class ClockHourMinute implements IClock {
    int hour = 0;
    int minute = 0;
    int cost = 0;
    String brand = "";

    Thread t;
    //Нужно ли сейчас потоку продолжать работу
    Boolean f1 = false;

    public void start() {
        if(t == null) {
            t = new Thread() {
                @Override
                public void run() {
                   f1 = true;

                   while(f1) {
                       try {
                           changeTime(0, 1, 0);
                       } catch (Exception ex) {
                           ex.fillInStackTrace();
                       }
                   }
                }
            };
        }

        t.start();
    }

    public void setHour(int hour) throws Exception {
        if(hour < 12 && hour >= 0) {
            this.hour = hour;
        } else {
            throw new Exception("Incorrect hour");
        }
    }

    public void setMinute(int minute) throws Exception {
        if(minute < 60 && minute >= 0) {
            this.minute = minute;
        } else {
            throw new Exception("Incorrect minuter");
        }
    }

    public void setSecond(int second) throws Exception {
        throw new Exception("There aren't seconds in this clock");
    }

    @Override
    public int getHour() {
        return hour;
    }

    @Override
    public int getMinute() {
        return minute;
    }

    @Override
    public int getSecond() throws Exception {
        throw new Exception("There aren't seconds in this clock");
    }

    @Override
    public void changeTime(int hour, int minute, int second) throws Exception {
        if (hour < 0 || minute < 0) {
            throw new Exception("Incorrect time");
        }

        int deltaHour = 0;
        if (this.minute + minute >= 60) {
            setMinute((this.minute + minute) - ((this.minute + minute) / 60) * 60);
            deltaHour = (this.minute + minute) / 60;
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

    @Override
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Override
    public void startClock() {

    }

    @Override
    public String toString() {
        return "ClockHourMinute{" +
                "hour = " + hour +
                ", minute = " + minute +
                '}';
    }
}
