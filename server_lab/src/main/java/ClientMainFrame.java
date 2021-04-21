import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientMainFrame extends JFrame {
    Thread t;
    Socket cs;
    OutputStream os;
    InputStream is;

    DataOutputStream dos;
    DataInputStream dis;

    int port = 3124;
    InetAddress host;

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private JPanel rootPanel;
    private JButton createAlarm;
    private JTextField hourAlarm;
    private JTextField minuteAlarm;
    private JTextField secondAlarm;
    private JPanel alarmPanel;
    private JLabel Time;
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JList listAlarms = new JList(listModel);

    public ClientMainFrame() {
        initComponents();

        createAlarm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAlarm();
            }
        });
    }

    public void initComponents() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Time.setFont(new Font("Serif", Font.PLAIN, 60));
        alarmPanel.add(new JScrollPane(listAlarms));
    }

    private void createAlarm() {
        if (t != null) {
            try {
                int h = Integer.parseInt(hourAlarm.getText());
                int m = Integer.parseInt(minuteAlarm.getText());
                int s = Integer.parseInt(secondAlarm.getText());
                Alarm alarm = new Alarm(s, m, h);


               String alarm_string = gson.toJson(alarm);
               dos.writeUTF(alarm_string);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connect() {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        try {
            cs = new Socket(host, port);
            os = cs.getOutputStream();
            dos = new DataOutputStream(os);

            t = new Thread() {
                @Override
                public void run() {

                    try {
                        is = cs.getInputStream();
                        dis = new DataInputStream(is);

                        while (true) {
                            String s = dis.readUTF();
                            Resp r = gson.fromJson(s, Resp.class);
                            newTime(r.time);

                            for (String alarm : r.getListAlarms()) {
                                addAlarm(alarm);
                            }

                            if (r.alarmFlag) {
                                createAlarmDialog();
                            }
                        }
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void createAlarmDialog() {
        JOptionPane.showMessageDialog(this,
                "The alarm clock is ringing!",
                "Alarm",
                JOptionPane.WARNING_MESSAGE);
    }

    void addAlarm(String alarm) {
        listModel.addElement(alarm);
    }

    void newTime(String time) {
        Time.setText(time);
    }

    public void startClient() {
        ClientMainFrame client = new ClientMainFrame();
        client.setVisible(true);
        client.setSize(500, 500);
        client.setLocationRelativeTo(null);
        client.connect();
    }
}
