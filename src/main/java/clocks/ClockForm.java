package clocks;

import com.sun.media.jfxmedia.logging.Logger;
import sun.rmi.runtime.Log;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class ClockForm extends JFrame {

    private JButton setAlarmButton;
    private JPanel rootPanel;
    private JPanel clockAlarm;
    private JTextField secondAlarm;
    private JTextField minuteAlarm;
    private JTextField hourAlarm;
    private JPanel Alarm;
    private JPanel Clock;
    private JLabel Calendar;
    ClockHourMinuteSecond clock2 = new ClockHourMinuteSecond();

    public ClockForm() {
        initComponents();
        setCurrentTime();
        setAlarmCheck();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClockForm().setVisible(true);
            }
        });
    }

    private void initComponents() {
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setAlarmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setAlarmButtonActionPerformed();
            }
        });
    }

    private void setAlarmButtonActionPerformed() {
        int h = Integer.parseInt(hourAlarm.getText());
        int m = Integer.parseInt(minuteAlarm.getText());
        int s = Integer.parseInt(secondAlarm.getText());
        clock2.setAlarm(new Alarm(s, m, h));
    }

    private void setAlarmCheck() {
        Thread alarmT;
        alarmT = new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    Alarm alarm = clock2.alarm;
                    if (clock2.ringOut()) {
                        System.out.println(true);
                        rootPanel.setBackground(Color.RED);
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        rootPanel.setBackground(Color.cyan);
                    } else {
                        System.out.println(false);
                    }
                }
            }
        };

        alarmT.start();
    }

    private void setCurrentTime() {
        try {
            clock2.setHour(9);
            clock2.setMinute(10);
            clock2.setSecond(59);
            clock2.startClock();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        Thread clockT;
        clockT = new Thread() {
            @Override
            public void run() {
                for (; ; ) {
                    try {
                        Calendar.setText(clock2.getHour() + ":"
                                + clock2.getMinute() + ":" + clock2.getSecond());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        clockT.start();
    }
}
