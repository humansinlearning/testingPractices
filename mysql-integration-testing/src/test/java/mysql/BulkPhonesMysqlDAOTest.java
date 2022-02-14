package mysql;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;

public class BulkPhonesMysqlDAOTest {

    private static final String DEFAULT_USER = "foo";
    private static final String DEFAULT_PASSWORD = "bar";

    private static final MysqldConfig CONFIG = aMysqldConfig(Version.v5_7_27)
        .withServerVariable("bind-address", "localhost")
        .withUser(DEFAULT_USER, DEFAULT_PASSWORD)
        .build();

    private final String schema = "humansinlearning";
    private final String initScriptResourcePath = "local/userDao.sql";

    private EmbeddedMysql mysql;
    private MysqlDataSource dataSource;
    private Statement stmt;
    int count;

    @Before
    public void arrangeSetup() throws SQLException {
        //Arrange
        generateMysqlEmbedded();

        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/?useCursorFetch=true", "foo", "bar");
        stmt = conn.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT count(*) as total FROM humansinlearning.users");
        while (rs.next()) {
            count = rs.getInt("total");
        }
        Assert.assertEquals(count, 2);
    }

    @Test
    public void bulkyTest() throws SQLException {

        //Act
        UserDao user = new UserDao("andrei", "con", "andrei.con");
        user.dbUrl = dataSource.getURL();
        user.dbUser = mysql.getConfig().getUsername();
        user.dbPwd = mysql.getConfig().getPassword();
        Assert.assertFalse(user.createUser(user));

        ResultSet rs3 = stmt.executeQuery("SELECT count(*) as total FROM humansinlearning.users");
        while (rs3.next()) {
            count = rs3.getInt("total");
        }
        //Assert
        Assert.assertEquals(3, count);
    }

    @After
    public void cleanupMysql() {
        if (mysql != null) {
            mysql.stop();
        }
    }

    private EmbeddedMysql generateMysqlEmbedded() {
        mysql = anEmbeddedMysql(CONFIG)
            .addSchema(schema, ScriptResolver.classPathScript(initScriptResourcePath))
            .start();

        dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3310);
        dataSource.setUser(DEFAULT_USER);
        dataSource.setPassword(DEFAULT_PASSWORD);
        dataSource.setDatabaseName(schema);
        return mysql;
    }
}
