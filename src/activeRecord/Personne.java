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

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                '}';
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    private void saveNew() throws SQLException
    {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Personne (nom, prenom) VALUES (?, ?)",  Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, this.nom);
        preparedStatement.setString(2, this.prenom);
        preparedStatement.executeUpdate();
        // met à jour l'attribut id de l'objet avec l'indice crée par la table grâce à l'auto-incrément
        int autoInc = -1;
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        this.id = autoInc;
    }

    private void update() throws SQLException
    {
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Personne SET nom = ?, prenom = ? WHERE id = ?  ");
        preparedStatement.setString(1, this.nom);
        preparedStatement.setString(2, this.prenom);
        preparedStatement.setInt(3, this.id);
        preparedStatement.execute();
    }

    public void save() throws SQLException {
        if (this.id == -1)
        {
            this.saveNew();
        }
        else
        {
            this.update();
        }
    }

    public void delete() throws SQLException
    {
        if (this.id != -1)
        {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Personne WHERE id = ?");
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            this.id = -1;
        }
    }

    public static void createTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE Personne ( id INTEGER primary key AUTO_INCREMENT , nom VARCHAR(25), prenom VARCHAR(30) ) ENGINE = InnoDB";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteTable()
    {
        Connection connection = DBConnection.getConnection();
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql = "DROP TABLE Personne";
        try {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
