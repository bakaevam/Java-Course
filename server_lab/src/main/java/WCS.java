import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class WCS extends Thread implements IObserver {
    ServerClass server;
    Socket cs;
    Model model;
    InputStream is;
    OutputStream os;
    DataInputStream dis;
    DataOutputStream dos;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public WCS(Model model, Socket cs, ServerClass server) {
        this.cs = cs;
        this.model = model;
        this.server = server;

        try {
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);
        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addObserver(this);
        this.start();
        sendAll(model.getAllAlarms());
    }

    @Override
    public void run() {
        try {
            is = cs.getInputStream();
            dis = new DataInputStream(is);

            while (true) {
                Resp r = new Resp();
                String obj = dis.readUTF();

                Alarm alarm = gson.fromJson(obj, Alarm.class);
                server.clock.setAlarm(alarm);
                model.addAlarm(alarm);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(Model model) {
        send(model.last());
    }

    public void updateTime(Model model) {
        sendTime(model.time, model.alarmFlag);
    }

    public void send(Alarm alarm) {
        try {
            Resp r = new Resp();
            r.addAlarm(String.valueOf(alarm));
            String alarm_string = gson.toJson(r);

            dos.writeUTF(alarm_string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTime(String time, boolean flag) {
        try {
            Resp r = new Resp();
            r.setNewTime(time);
            r.setAlarmFlag(flag);

            String time_string = gson.toJson(r);
            dos.writeUTF(time_string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendAll(ArrayList<Alarm> alarms) {
        try {
            Resp r = new Resp();
            for (Alarm alarm : alarms) {
                r.addAlarm(String.valueOf(alarm));
            }

            String alarm_string = gson.toJson(r);
            dos.writeUTF(alarm_string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
