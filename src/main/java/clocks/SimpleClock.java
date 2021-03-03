package clocks;

public class SimpleClock extends Clock {

    public void changeTime(int hours, int minutes) throws Exception {
        if (hours < 0 || minutes < 0) {
            throw new Exception("Incorrect time");
        }

        int deltaHour = 0;
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
