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

    ArrayList<Film> test1;

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

        test1 = new ArrayList<>();
        test1.add(f);
        test1.add(f2);
    }

    @Test
    public void test1_findByRealisateur() throws SQLException
    {
        ArrayList<Film> listFilm = Film.findByRealisateur(p);

        // Recuperer les deux films cree dans init (avec la méthode findById) et verifier qu'ils sont bien dans l'arrayList "listFilm"
        Film f1 = Film.findById(1);
        Assertions.assertEquals(f1.toString(), listFilm.get(0).toString());

        Film f2 = Film.findById(2);
        Assertions.assertEquals(f2.toString(), listFilm.get(1).toString());
    }

    @Test
    public void test2_creerFilm() throws SQLException, RealisateurAbsentException {
        Film film = new Film("Titanic", p);
        film.save();
        // verifier que la methode findById retourne bien p(Steven Spielberg)
        Film f1 = Film.findById(film.getId());
        Assertions.assertEquals(film.toString(), f1.toString());
    }

    @Test
    public void test3_delete() throws SQLException {
        int id = f.getId();
        f.delete();
        Assertions.assertNull(Film.findById(id));
    }

    @Test
    public void test4_update() throws SQLException, RealisateurAbsentException {
        f2 = new Film("eleven", p);
        f2.save();
        Film f22 = Film.findById(f2.getId());
        Assertions.assertEquals(f2.toString(), f22.toString());
    }

    @AfterEach
    public void after() throws SQLException {
        Film.deleteTable();
        Personne.deleteTable();
    }
}
