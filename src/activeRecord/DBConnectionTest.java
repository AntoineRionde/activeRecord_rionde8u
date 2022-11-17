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
        boolean bTrue = (connection1 == connection2) ? true : false;
        Assertions.assertTrue(bTrue);

    }
}
