package activeRecord;

import java.sql.*;
import java.util.ArrayList;

public class Film {
    private String titre;
    private int id; // id du film
    private int id_real; // id du realisateur

    public Film(String titre, Personne realisateur)
    {
        this.titre = titre;
        this.id_real = realisateur.getId();
        this.id = -1;
    }

    private Film(int id, int id_real, String titre)
    {
        this.id = id;
        this.id_real = id_real;
        this.titre = titre;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public static Film findById(int id) throws SQLException {
        Film film = null;
        String sql = "SELECT * FROM Film WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            int id2 = resultSet.getInt(1);
            String titre = resultSet.getString(2);
            int id_real = resultSet.getInt(3);
            film = new Film(id2, id_real, titre);
        }
        return film;
    }

    public Personne getRealisateur() throws SQLException {
        return Personne.findById(this.id_real);
    }

    public static void createTable() throws SQLException
    {
        Connection connection = DBConnection.getConnection();
        Statement statement = connection.createStatement();
        String sql = "CREATE TABLE Film (id INTEGER primary key AUTO_INCREMENT, titre varchar(40) NOT NULL, id_rea INTEGER DEFAULT NULL) ENGINE=InnoDB";
        statement.execute(sql);
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = null;
        statement = connection.createStatement();
        String sql = "DROP TABLE Film";
        statement.execute(sql);
    }

    public void delete() throws SQLException
    {
        if (this.id != -1)
        {
            Connection connection = DBConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM Film WHERE id = ?");
            preparedStatement.setInt(1, this.id);
            preparedStatement.execute();
            this.id = -1;
        }
    }

    private void saveNew() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1) throw new RealisateurAbsentException();

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Film (titre, id_rea) VALUES (?, ?)",  Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, this.titre);
        preparedStatement.setInt(2, this.id_real);
        preparedStatement.executeUpdate();
        // met à jour l'attribut id de l'objet avec l'indice crée par la table grâce à l'auto-incrément
        int autoInc = -1;
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if (rs.next()) {
            autoInc = rs.getInt(1);
        }
        this.id = autoInc;
    }

    private void update() throws SQLException, RealisateurAbsentException {
        if (this.id_real == -1) throw new RealisateurAbsentException();

        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Film SET titre = ?, id_rea = ? WHERE id = ?");
        preparedStatement.setString(1, this.titre);
        preparedStatement.setInt(2, this.id_real);
        preparedStatement.setInt(3, this.id);
        preparedStatement.execute();
    }

    public void save() throws SQLException, RealisateurAbsentException {
        if (this.id == -1)
        {
            this.saveNew();
        }
        else
        {
            this.update();
        }
    }

    public static ArrayList<Film> findByRealisateur(Personne p) throws SQLException {
        ArrayList<Film> arrayList = new ArrayList<>();
        int id_real = p.getId();
        String sql = "SELECT * FROM Film WHERE id_real = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id_real);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next())
        {
            int idF = resultSet.getInt(1);
            String titre = resultSet.getString(2);
            arrayList.add(new Film(idF, id_real, titre));
        }
        return arrayList;

    }


}
