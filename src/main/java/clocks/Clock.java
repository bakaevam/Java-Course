package clocks;

public abstract class Clock {
    private int cost;
    private String watchBrand;

    protected int hour;
    protected int minute;

    public void setHour(int hour) throws Exception {
        if (hour >= 0 && hour <= 24) {
            this.hour = hour;
        } else {
            throw new Exception("Incorrect hour");
        }
    }

    public void setMinute(int minute) throws Exception {
        if (minute >= 0 && minute <= 60) {
            this.minute = minute;
        } else {
            throw new Exception("Incorrect minute");
        }
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setWatchBrand(String watchBrand) {
        this.watchBrand = watchBrand;
    }

    public int getCost() {
        return cost;
    }

    public String getWatchBrand() {
        return watchBrand;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    protected void changeMinute(int deltaMinute) {
        minute += deltaMinute;
    }

    protected void changeHour(int deltaHour) {
        hour += deltaHour;
    }
}
