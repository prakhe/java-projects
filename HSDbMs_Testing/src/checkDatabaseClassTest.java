import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class checkDatabaseClassTest {

    private static Connection c;
    private static checkDatabaseClass checkDB;
    private static calibDatabaseClass calibDB;
    private static connectionClass conCLass;

    @BeforeAll
    static void executeBefore() throws SQLException, ClassNotFoundException {
        conCLass = new connectionClass();
        c= conCLass.createConnection(c,"root","root","");
        checkDB= new checkDatabaseClass();
        calibDB= new calibDatabaseClass();
    }

    @Test
    void testingDB() throws SQLException, ClassNotFoundException {
        //checking for database, creating it if not found!
        assertFalse(checkDB.checkDatabaseExists(c,"societymanagement"));
        System.out.println("Database checked!");

        assertEquals(1,calibDB.createDatabase(c,"societymanagement"));
        System.out.println("Assuming it does not exist, creating a new database!");

        assertTrue(checkDB.checkDatabaseExists(c,"societymanagement"));
        System.out.println("Database was created!");

        //check for table, creating it if not found!
        c= conCLass.createConnection(c,"root","root","societymanagement");
        assertFalse(checkDB.checkTableExists(c,"myTable"));
        System.out.println("Table checked!");

        calibDB.createTable(c,"myTable");
        System.out.println("Assuming it does not exist, creating a new table!");

        assertTrue(checkDB.checkTableExists(c,"myTable"));
        System.out.println("Table was created!");

        //check if the table has any data, inserting default if not found!
        assertTrue(checkDB.checkTableEmpty(c,"myTable"));
        assertNotEquals(0,calibDB.calibTable(c,"myTable"));
        System.out.println("Table was calibrated!");
    }
}