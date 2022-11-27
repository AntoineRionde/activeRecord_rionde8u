package activeRecord;

import java.sql.*;

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

    public static Film findById(int id) throws SQLException {
        Film film = null;
        String sql = "SELECT * FROM Film WHERE id = ?";
        Connection connection = DBConnection.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.getResultSet();
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
        String sql = "CREATE TABLE Film (id INTEGER primary key AUTO INCREMENT, titre varchar(40) NOT NULL, id_rea INTEGER DEFAULT NULL) ENGINE=InnoDB";
        statement.execute(sql);
    }

    public static void deleteTable() throws SQLException {
        Connection connection = DBConnection.getConnection();
        Statement statement = null;
        statement = connection.createStatement();
        String sql = "DROP TABLE Film";
        statement.execute(sql);
    }


}
