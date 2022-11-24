package activeRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    public Film findById(int id) throws SQLException {
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


}
