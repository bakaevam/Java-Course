package clocks;

import static clocks.ClockType.*;

public class BuilderClock {
    public static IClock build(ClockType type) {
        if(type == HM) {
            return new ClockHourMinute();
        }
        if(type == HMS) {
            return new ClockHourMinuteSecond();
        }
        return null;
    }
}
