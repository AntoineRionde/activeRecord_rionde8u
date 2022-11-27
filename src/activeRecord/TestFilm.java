package activeRecord;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class TestFilm {

    @BeforeEach
    public void init() throws SQLException {
        Film.createTable();
    }

    @AfterEach
    public void after() throws SQLException {
        Film.deleteTable();
    }
}
