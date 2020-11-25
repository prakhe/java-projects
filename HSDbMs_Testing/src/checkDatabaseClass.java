import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class checkDatabaseClass {

    boolean checkDatabaseExists(Connection c, String checkName) throws SQLException {
        String dbname="";
        ResultSet rs= c.getMetaData().getCatalogs();
        while(rs.next()){
            dbname= rs.getString(1);
            if(dbname.equalsIgnoreCase(checkName))
                return true;
        }
        return false;
    }

    boolean checkTableExists(Connection c, String table) throws SQLException {
        String tbname="";
        ResultSet rs= c.getMetaData().getTables("societymanagement",null,"%",null);
        while(rs.next())
        {
            tbname= rs.getString(3);
            if(tbname.equalsIgnoreCase(table))
                return true;
        }
        return false;
    }

    boolean checkTableEmpty(Connection c, String table) throws SQLException {
        String sql= "select * from "+table;
        Statement stmt= c.createStatement();
        return stmt.execute(sql);
    }
}
