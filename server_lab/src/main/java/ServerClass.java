import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerClass {
    int port = 3124;
    InetAddress host;

    ClockHourMinuteSecond clock = new ClockHourMinuteSecond();

    Model model = new Model();

    public ServerClass() {
        try {
            //По какому ip адрессу будет работать сервер
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            ServerSocket ss = new ServerSocket(port, 0, host);
            setCurrentTime();
            System.out.println("Server started");

            while (true) {
                // Дождаться подключения удаленного пользователя
                // Общение с клиентом
                Socket cs = ss.accept();
                System.out.println("Client connected");

                WCS wcs = new WCS(model, cs, this);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCurrentTime() {
        try {
            clock.setHour(9);
            clock.setMinute(10);
            clock.setSecond(59);
            clock.startClock();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Thread clockT;
        clockT = new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        model.addTime(clock.toString(), clock.checkAlarms());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        clockT.start();
    }

    public static void main(String[] args) {
        ServerClass ss = new ServerClass();
    }
}
