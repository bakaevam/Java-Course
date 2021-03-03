package clocks;

public class ClockWithSeconds extends Clock {
    private int second;

    public int getSeconds() {
        return second;
    }

    public void setSeconds(int second) throws Exception {
        if (second >= 0 && second <= 60) {
            this.second = second;
        } else {
            throw new Exception("Incorrect seconds");
        }
    }

    protected void changeSecond(int deltaSecond) {
        second += deltaSecond;
    }

    public void changeTime(int hours, int minutes, int seconds) throws Exception {
        if (hours < 0 || minutes < 0 || seconds < 0) {
            throw new Exception("Incorrect time");
        }

        int deltaHour = 0;
        int deltaMinute = 0;

        if (seconds + second >= 60) {
            setSeconds((seconds + second) - ((seconds + second) / 60) * 60);
            deltaMinute = (seconds + second) / 60;
        } else {
            changeSecond(seconds);
        }

        minutes += deltaMinute;
        if (minute + minutes >= 60) {
            setMinute((minutes + minute) - ((minutes + minute) / 60) * 60);
            deltaHour = (minutes + minute) / 60;
        } else {
            changeMinute(minutes);
        }

        hours += deltaHour;
        if (hour + hours >= 24) {
            setHour((hours + hour) - ((hour + hours) / 24) * 24);
        } else {
            changeHour(hours);
        }
    }
}
