package mysql.embedded;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import mysql.UserDao;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;

public class BulkPhonesMysqlTest {

//    https://github.com/wix/wix-embedded-mysql

    private static final String DEFAULT_USER = "foo";
    private static final String DEFAULT_PASSWORD = "bar";

    private static final MysqldConfig CONFIG = aMysqldConfig(Version.v5_7_27)
        .withServerVariable("bind-address", "localhost")
        .withUser(DEFAULT_USER, DEFAULT_PASSWORD)
        .withTimeZone("Europe/Amsterdam")
        .build();

    private final String schema = "humansinlearning";
    private final String initScriptResourcePath = "local/userDao.sql";

    private EmbeddedMysql mysql;
    private MysqlDataSource dataSource;


    
    @Test
    public void bulkyTest() throws SQLException {

        mysql = anEmbeddedMysql(CONFIG)
            .addSchema(schema, ScriptResolver.classPathScript(initScriptResourcePath))
            .start();

        dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3310);
        dataSource.setUser(DEFAULT_USER);
        dataSource.setPassword(DEFAULT_PASSWORD);
        dataSource.setDatabaseName(schema);

        int count = 0;
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3310/?useCursorFetch=true",
            DEFAULT_USER, DEFAULT_PASSWORD);
//        Connection conn = DriverManager.getConnection("jdbc:mysql://"+dataSource.getServerName()+ ":" + dataSource.getPort() +"/?useCursorFetch=true",
//            DEFAULT_USER, DEFAULT_PASSWORD);
        Statement stmt = conn.createStatement();

        UserDao user = new UserDao("dude","code","dude.codes");
        user.dbUrl = "jdbc:mysql://localhost:3310/humansinlearning";
        user.dbUser = DEFAULT_USER;
        user.dbPwd = DEFAULT_PASSWORD;

        boolean insertedFlag = user.createUser(user);
        Assert.assertFalse(insertedFlag);

        ResultSet rs = stmt.executeQuery("SELECT count(*) as total FROM humansinlearning.users");
        while (rs.next()) {
            count = rs.getInt("total");
        }
        Assert.assertEquals(3, count);

        if (mysql != null) {
            mysql.stop();
        }
    }
}
