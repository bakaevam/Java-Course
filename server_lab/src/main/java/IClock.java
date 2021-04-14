public interface IClock {
    void setHour(int hour) throws Exception;

    void setMinute(int minute) throws Exception;

    void setSecond(int second) throws Exception;

    int getHour();

    int getMinute();

    int getSecond() throws Exception;

    void changeTime(int hour, int minute, int second) throws Exception;

    void setCost(int cost);

    void setBrand(String brand);

    void startClock();
}
