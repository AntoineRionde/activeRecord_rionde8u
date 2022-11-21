package activeRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;
    static String nomDb;

    public static Connection getConnection() {

        if (connection == null)
        {
            // variables a modifier en fonction de la base
            String username = "root";
            String password = "";
            String serverName = "localhost";
            // Attention, sous MAMP, le port est 8889
            String portNumber = "3306";
            String tableName = "personne";

            // iL faut une base nommee testPersonne !
            nomDb = "testpersonne";
            // creation de la connection
            Properties connectionProps = new Properties();
            connectionProps.put("user", username);
            connectionProps.put("password", password);
            String urlDB = "jdbc:mysql://" + serverName + ":";
            urlDB += portNumber + "/" + nomDb;
            try {
                connection = DriverManager.getConnection(urlDB, connectionProps);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return connection;

    }

    public static void setNomDB(String nomDB) {
        // change l'attribut
        nomDb = nomDB;
        connection = getConnection();
    }
}
