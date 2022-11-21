package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public class TestPersonne {
    @BeforeEach
    public void init() throws SQLException {
        Personne.createTable();
        Personne p1 = new Personne("Spielberg", "Steven");
        p1.save();
        Personne p2 = new Personne("Scott", "Ridley");
        p2.save();
        Personne p3 = new Personne("Kubrick", "Stanley");
        p3.save();
        Personne p4 = new Personne("Fincher", "David");
        p4.save();
    }

    @AfterEach
    public void after()
    {
        Personne.deleteTable();
    }
}
