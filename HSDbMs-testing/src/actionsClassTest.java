import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class actionsClassTest {
    private static actionsClass actionsClass;

    @org.junit.jupiter.api.BeforeAll
    static void BeforeAll() {
        actionsClass= new actionsClass();
    }

    @org.junit.jupiter.api.BeforeEach
    void BeforeEach() throws SQLException, ClassNotFoundException {
        createConnection();
    }

    @org.junit.jupiter.api.Test
    void createConnection() throws SQLException, ClassNotFoundException {
        assertTrue(actionsClass.createConnection("myuser","pass"));
    }

    @org.junit.jupiter.api.Test
    void isCalib() throws SQLException, ClassNotFoundException {
        assertNotNull(actionsClass.isCalib());
    }

    @org.junit.jupiter.api.Test
    void checkFlat() throws SQLException, ClassNotFoundException {
        assertTrue(actionsClass.checkFlat("A",101));
        assertFalse(actionsClass.checkFlat("A",401));
    }

    @org.junit.jupiter.api.Test
    void setData() throws SQLException, ClassNotFoundException {
        assertEquals(1,actionsClass.setData("A",101,"Sam",99800,true));
        assertEquals(0,actionsClass.setData("F",105,"Tej",99800,true));
    }

    @org.junit.jupiter.api.Test
    void remove() throws SQLException, ClassNotFoundException {
        assertEquals(1,actionsClass.remove("A",101));
        assertEquals(0,actionsClass.remove("F",105));
    }
}