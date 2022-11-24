package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

    @Test
    public void test1_findById() throws SQLException
    {
        Personne p = Personne.findById(4);
        Assertions.assertNotNull(p);
    }

    @Test
    public void test2_findAll() throws SQLException {
        ArrayList<Personne> arrayList = Personne.findAll();
        // verifier que la quatrième personne correspond bien a la personne a l'indice 3 dans l'array list
        Personne pers4 = arrayList.get(3);
        Assertions.assertEquals(pers4.toString(), Personne.findById(4).toString());
    }

    @Test
    public void test3_findByName() throws SQLException
    {
        ArrayList<Personne> arrayList = Personne.findByName("Kubrick");
        // verifier que la personne contenu dans arrayList correspond bien a la personne a l'id 3
        Personne pers3 = arrayList.get(0);
        Assertions.assertEquals(pers3.toString(), Personne.findById(3).toString());
    }

    @Test
    public void test4_update() throws SQLException
    {
        // on recupere une personne existante, on la met à jour avec save() vu qu'il rentre dans la fonction update
        Personne personne2 = Personne.findById(2);
        personne2.setNom("Jean");
        personne2.setPrenom("Bernard");
        personne2.save();
        Assertions.assertNotNull(personne2);
        System.out.println(personne2);
    }

    @Test
    public void test5_delete() throws SQLException
    {
        Personne personne1 = Personne.findById(1);
        personne1.delete();
        Assertions.assertEquals(-1, personne1.getId());
    }

    @AfterEach
    public void after()
    {
        Personne.deleteTable();
    }
}
