import java.sql.*;

class actionsClass {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement pstmt;
    private String sql;
    private ResultSet rset;

    actionsClass() {
        connection= null;
        pstmt= null;
        sql= "";
    }

    boolean createConnection(String user, String pass) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/societymanagement?autoReconnect=true&useSSL=false",user,pass);
        return connection != null;
    }

    ResultSet isCalib() throws SQLException, ClassNotFoundException {
        sql= "select * from mytable";
        stmt= connection.createStatement();
        return stmt.executeQuery(sql);
    }

    boolean checkFlat(String wing, int f_no) throws SQLException, ClassNotFoundException {
        sql="select * from mytable where wing= ? and fno= ?";
        pstmt= connection.prepareStatement(sql);
        pstmt.setString(1,wing);
        pstmt.setInt(2,f_no);
        rset= pstmt.executeQuery();
        return rset.next();
    }

    int setData(String wing, int f_no, String name, long phone, boolean rent) throws SQLException, ClassNotFoundException {
        sql= "update mytable set name = ?, phone = ?, rent = ? where wing = ? and fno = ?";
        pstmt= connection.prepareStatement(sql);
        pstmt.setString(1,name);
        pstmt.setLong(2,phone);
        pstmt.setBoolean(3,rent);
        pstmt.setString(4,wing);
        pstmt.setInt(5,f_no);
        return pstmt.executeUpdate();
    }

    int remove(String wing, int f_no) throws SQLException, ClassNotFoundException {
        sql="update mytable set name = NULL, phone = NULL, rent = NULL where wing = ? and fno = ?";
        pstmt= connection.prepareStatement(sql);
        pstmt.setString(1,wing);
        pstmt.setInt(2,f_no);
        return pstmt.executeUpdate();
    }
}
