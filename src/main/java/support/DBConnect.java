package support;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static Connection dbConn;
    private static Dotenv config;


    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        config = Dotenv.configure().load();

        String url = "jdbc:mariadb://dsm.moonlight.one:6133/d-day";
        String user = config.get("DBID");
        String pw = config.get("DBPW");
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("DB접속 테스트");
        dbConn = DriverManager.getConnection(url, user, pw);
        return dbConn;
    }
}