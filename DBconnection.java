package hrs_re_2;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class DBconnection {
	static Connection con;
	private DBconnection() {};

    static Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/hrs_db",
                "root",
                "grizzywizzy"
            );
        }
        return con;
    }
}
