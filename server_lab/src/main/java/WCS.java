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
                String obj = dis.readUTF();
                Request r = gson.fromJson(obj, Request.class);
                if (r.state == AlarmState.CREATE) {
                    System.out.println("WCS CREATE");
                    server.clock.setAlarm(r.alarm);
                    model.addAlarm(r.alarm);
                }

                if (r.state == AlarmState.DELETE) {
                    System.out.println("WCS DELETE");
                    if (!r.deleteFromDB) {
                        model.deleteAlarm(r.alarm, r.idxDelete);
                        server.clock.deleteAlarm(r.alarm);
                    } else {
                        model.deleteAlarmAfterRinging(r.alarm, r.idxDelete);
                    }
                }
            }
        } catch (IOException e) {
           // e.printStackTrace();
        }
    }

    public void update(Model model) {
        send(model.last());
    }

    public void updateTime(Model model) {
        sendTime(model.time, model.alarmFlag);
    }

    public void updateAfterDelete(Model model) {
        try {
            Resp r = new Resp();
            r.setIdNeededDelete(true);
            r.setIdxDeleteAlarm(model.idxDelete);
            String deleteAlarmString = gson.toJson(r);

            dos.writeUTF(deleteAlarmString);
        } catch (IOException e) {
           // e.printStackTrace();
        }
    }

    public void updateAfterRinging(Model model) {
        try {
            Resp r = new Resp();
            r.setIdxDeleteAlarm(model.idxDelete);
            String deleteAlarmString = gson.toJson(r);

            dos.writeUTF(deleteAlarmString);
        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    public void send(Alarm alarm) {
        try {
            Resp r = new Resp();
            r.addAlarm(String.valueOf(alarm));
            String alarm_string = gson.toJson(r);

            dos.writeUTF(alarm_string);
        } catch (IOException e) {
            //e.printStackTrace();
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
            //e.printStackTrace();
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
           // e.printStackTrace();
        }
    }
}
