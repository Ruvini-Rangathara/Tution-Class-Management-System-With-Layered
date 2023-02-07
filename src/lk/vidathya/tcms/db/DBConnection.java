package lk.vidathya.tcms.db;

import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection(){

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tcms?allowPublicKeyRetrieval=true&useSSL=false", "root", "1234");
        } catch ( SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR,e+"").show();
        }

    }
    public static DBConnection getInstance() {
        return (null == dbConnection) ? dbConnection = new DBConnection() : dbConnection;
    }
    public Connection getConnection() {
        return connection;
    }
}
