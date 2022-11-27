package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;

public class TestFilm {

    Film f;
    Film f2;
    Personne p;
    @BeforeEach
    public void init() throws SQLException, RealisateurAbsentException {
        Film.createTable();
        Personne.createTable();
        p = new Personne("Spielberg", "Steven");
        p.save();
        f = new Film("seven", p);
        f.save();
        f2 = new Film("ten", p);
        f2.save();
    }

    @Test
    public void test1_findByRealisateur() throws SQLException
    {
        ArrayList<Film> arrayList = Film.findByRealisateur(p);
        Film f = Film.findById(2);
        // verifier que la deuxième film cree dans la base correspond bien au film a l'indice 1 dans l'array list
        Assertions.assertEquals(f.toString(), arrayList.get(1).toString());
    }

    @AfterEach
    public void after() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }
}
