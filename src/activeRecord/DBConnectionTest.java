package activeRecord;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

public class DBConnectionTest {

    Connection connection1;
    Connection connection2;

    @BeforeEach
    public void init()
    {
        connection1 = DBConnection.getConnection();
        connection2 = DBConnection.getConnection();
    }

    @Test
    public void test1_memeObjet()
    {
        Assertions.assertTrue(connection1 == connection2);
    }

    @Test
    public void test2_changerBase()
    {
        String nomDbAvant = DBConnection.nomDb;
        DBConnection.setNomDB("testpersonne2");

        String nomDbApres = DBConnection.nomDb;
        Assertions.assertFalse(nomDbAvant.equals(nomDbApres));
    }
}
