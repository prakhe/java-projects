import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class connectionClass {

    boolean checkConnection(Connection c) throws SQLException {
        return !c.isClosed();
    }

    Connection createConnection(Connection con, String user, String pass, String dbname) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        if(dbname.equalsIgnoreCase(""))
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/?autoReconnect=true&useSSL=false", user, pass);
        else
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+dbname+"?autoReconnect=true&useSSL=false", user, pass);
        return con;
    }

    boolean closeConnection(Connection c) throws SQLException {
        if(!checkConnection(c))
            return false;
        else
            c.close();
        return true;
    }

}