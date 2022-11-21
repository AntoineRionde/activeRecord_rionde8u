package activeRecord;

import org.junit.jupiter.api.Assertions;

import java.sql.*;
import java.util.ArrayList;

public class Personne {
    private int id;
    private String nom;
    private String prenom;

    public Personne(String pNom, String pPren)
    {
        this.nom = pNom;
        this.prenom = pPren;
        this.id = -1;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public static ArrayList<Personne> findAll() throws SQLException {
        ArrayList<Personne> arrayList = new ArrayList<>();
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM personne ";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next())
        {
            int id = resultSet.getInt(1);
            String nom = resultSet.getString(2);
            String prenom = resultSet.getString(3);
            Personne p = new Personne(nom, prenom);
            p.setId(id);

            arrayList.add(p);
        }
        //connection.close();
        return arrayList;
    }

    public static Personne findById(int id) throws SQLException {
        Personne res = null;
        String sql = "SELECT * FROM personne WHERE ID = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            int idP = resultSet.getInt(1);
            String nom = resultSet.getString(2);
            String prenom = resultSet.getString(3);
            res = new Personne(nom, prenom);
            res.setId(idP);
        }
        return res;
    }

    public static ArrayList<Personne> findByName(String pNom) throws SQLException {
        Connection connection = DBConnection.getConnection();
        ArrayList<Personne> arrayList = new ArrayList<>();
        String sql = "SELECT * FROM personne WHERE NOM = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, pNom);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            int id = resultSet.getInt(1);
            String nom = resultSet.getString(2);
            String prenom = resultSet.getString(3);
            Personne p = new Personne(nom, prenom);
            p.setId(id);
            arrayList.add(p);
        }
        //connection.close();
        return arrayList;
    }


}
