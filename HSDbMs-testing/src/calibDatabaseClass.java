import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class calibDatabaseClass {

    int createDatabase(Connection c, String dbname) throws SQLException {
        String sql = "";
        int retVal= 0;
        Statement stmt= c.createStatement();
        try
        {
            sql= "create database "+dbname;
            retVal= stmt.executeUpdate(sql);
        }
        catch(Exception ignored){}
        return retVal;
    }

    void createTable(Connection c, String table) throws SQLException {
        String sql= "create table "+table+" (wing char(1), fno int(3), name varchar(20), phno int(11))";
        Statement stmt= c.createStatement();
        stmt.execute(sql);
    }

    int calibTable(Connection c, String table) throws SQLException {
        String sql="insert into "+table+" (wing, fno, name, phno) values ('A',101,NULL,NULL),('A',102,NULL,NULL),('A',103,NULL,NULL),('A',104,NULL,NULL)" +
                ",('B',101,NULL,NULL),('B',102,NULL,NULL),('B',103,NULL,NULL),('B',104,NULL,NULL)" +
                ",('C',101,NULL,NULL),('C',102,NULL,NULL),('C',103,NULL,NULL),('C',104,NULL,NULL)";
        Statement stmt= c.createStatement();
        return stmt.executeUpdate(sql);
    }
}
