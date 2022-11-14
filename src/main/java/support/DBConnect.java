package support;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection dbConn;

    public static Connection getConnection() throws SQLException, ClassNotFoundException {

        String url = "jdbc:mariadb://dsm.moonlight.one:6133/d-day";
        String user = serects.dbid();
        String pw = serects.dbpw();
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("DB접속 테스트");
        dbConn = DriverManager.getConnection(url, user, pw);
        return dbConn;
    }
}