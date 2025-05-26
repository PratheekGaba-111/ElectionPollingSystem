import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        String url = "jdbc:mysql://localhost:3306/election_db?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "Prathu@0311";

        Class.forName("com.mysql.cj.jdbc.Driver");  // optional but recommended
        return DriverManager.getConnection(url, username, password);
    }
}

