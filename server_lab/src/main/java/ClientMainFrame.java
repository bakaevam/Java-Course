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
    private JPanel alarmListPanel;
    private DefaultListModel<String> listModel = new DefaultListModel<String>();
    private JList listAlarms = new JList(listModel);
    private JButton deleteAlarmButton = new JButton("Удалить");
    private JPanel buttonPanel = new JPanel();

    public ClientMainFrame() {
        initComponents();

        createAlarm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                createAlarm();
            }
        });
    }

    Alarm fromStringToAlarm(String str) {
        int h = Integer.parseInt(str.substring(0, str.indexOf(':')));
        int m = Integer.parseInt(str.substring(str.indexOf(':') + 1, str.lastIndexOf(':')));
        int s = Integer.parseInt(str.substring(str.lastIndexOf(':') + 1));

        return new Alarm(s, m, h);
    }

    void deleteAlarm(int idx) {
        System.out.println("DELETE ALARM IDX " + idx);
        String alarmStr = listModel.get(idx);
        Alarm deleteAlarm = fromStringToAlarm(alarmStr);
        System.out.println(deleteAlarm + " DELETE");
        // Delete from db and clock
        try {
            Request r = new Request();
            r.setAlarm(deleteAlarm);
            r.setState(AlarmState.DELETE);
            r.setIdxDelete(idx);
            r.setDeleteFromDB(false);
            String alarm_delete = gson.toJson(r);
            dos.writeUTF(alarm_delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void deleteAlarmAfterRinging(int idx) {
        System.out.println("DELETE ALARM IDX " + idx);
        String alarmStr = listModel.get(idx);
        Alarm deleteAlarm = fromStringToAlarm(alarmStr);
        System.out.println(deleteAlarm + " DELETE");
        // Delete from db and clock
        try {
            Request r = new Request();
            r.setAlarm(deleteAlarm);
            r.setState(AlarmState.DELETE);
            r.setIdxDelete(idx);
            r.setDeleteFromDB(true);
            String alarm_delete = gson.toJson(r);
            dos.writeUTF(alarm_delete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initComponents() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Time.setFont(new Font("Serif", Font.PLAIN, 60));
        alarmListPanel.setLayout(new BorderLayout(10, 10));
        alarmListPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        alarmListPanel.add(new JScrollPane(listAlarms));
        buttonPanel.setLayout(new GridLayout(1,1,5, 0));
        alarmListPanel.add(buttonPanel, BorderLayout.SOUTH);

        deleteAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteAlarm(listAlarms.getSelectedIndex());
            }
        });

        buttonPanel.add(deleteAlarmButton);
    }

    private void createAlarm() {
        if (t != null) {
            try {
                int h = Integer.parseInt(hourAlarm.getText());
                int m = Integer.parseInt(minuteAlarm.getText());
                int s = Integer.parseInt(secondAlarm.getText());
                Alarm alarm = new Alarm(s, m, h);

                Request r = new Request();
                r.setAlarm(alarm);
                r.setState(AlarmState.CREATE);
                String alarm_create = gson.toJson(r);
                dos.writeUTF(alarm_create);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void connect() {
        try {
            host = InetAddress.getLocalHost();
            System.out.println(host);
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
                                deleteAlarmAfterRinging(listModel.indexOf(r.time));
                                listModel.removeElement(r.time);
                                createAlarmDialog();
                            }

                            if (r.idNeededDelete) {
                                listModel.remove(r.idxDeleteAlarm);
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
