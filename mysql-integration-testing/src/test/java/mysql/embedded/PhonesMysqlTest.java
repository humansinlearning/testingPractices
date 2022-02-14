package mysql.embedded;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PhonesMysqlTest {

    @ClassRule
    public static final EmbeddedMysqlResource RESOURCE = new EmbeddedMysqlResource("humansinlearning", "local/userDao.sql");

    @Test
    public void test() throws SQLException {
        int count = 0;
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/?useCursorFetch=true", "foo", "bar");
        Statement stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT count(*) as total FROM humansinlearning.users");
        while (rs.next()) {
            count = rs.getInt("total");
        }
        Assert.assertEquals(count, 2);
    }
}
