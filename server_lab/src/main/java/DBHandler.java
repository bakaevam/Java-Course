import java.sql.*;
import java.util.ArrayList;

public class DBHandler {
    private static final String ADDRESS_CONNECTION = "jdbc:sqlite:" +
            "C:\\Users\\mary396969\\Desktop\\sqlitestudio-3.3.3\\SQLiteStudio\\alarmsdb.db";

    // Singleton
    private static DBHandler instance = null;

    public  static  synchronized DBHandler getInstance() {
        if (instance == null) {
            instance = new DBHandler();
        }
        return instance;
    }

    private Connection connection;

    private DBHandler() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(ADDRESS_CONNECTION);
            System.out.println("Opened db connection successfully");
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public ArrayList<Alarm> getListFromDB() {
        ArrayList<Alarm> resList = new ArrayList<Alarm>();
        try {
            Statement st = connection.createStatement();
            ResultSet res = st.executeQuery("select * from alarms");
            while (res.next()) {
                Alarm a = new Alarm(res.getInt("second"),
                        res.getInt("minute"), res.getInt("hour"));
                System.out.println(a);
                resList.add(a);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return resList;
    }

    public void saveAlarmToDB(Alarm alarm) {
        try {
            PreparedStatement pst =
                    connection.prepareStatement("insert into alarms('hour', 'minute', 'second')" +
                            "values(?, ?, ?)");
            pst.setObject(1, alarm.hh);
            pst.setObject(2, alarm.mm);
            pst.setObject(3, alarm.ss);

            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void deleteAlarmFromDB(Alarm alarm) {
        try {
            PreparedStatement pst =
                    connection.prepareStatement("delete from alarms where hour = ?, minute = ?, second = ?");
            pst.setObject(1, alarm.hh);
            pst.setObject(2, alarm.mm);
            pst.setObject(3, alarm.ss);

            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
