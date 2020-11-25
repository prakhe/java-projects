import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class connectionClassTest {

    private static Connection c;
    private static connectionClass obj;

    @BeforeEach
    void executeBefore() throws SQLException, ClassNotFoundException {
        obj= new connectionClass();
        c= obj.createConnection(c,"root","root","");
    }

    @Test
    void createConnection() {
        assertNotNull(c);
        System.out.println("Connection-> create tested!");
    }

    @Test
    void checkConnection() throws SQLException {
        assertTrue(obj.checkConnection(c));
        System.out.println("Connection-> check tested!");
    }

    @Test
    void closeConnection() throws SQLException {
        assertTrue(obj.closeConnection(c));
        System.out.println("Connection-> close tested!");
    }
}