package mysql.testcontainers.after;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.utility.DockerImageName;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public class SimpleMysqlTest extends AbstractContainerDatabaseTest {

    private static final Logger logger = LoggerFactory.getLogger(SimpleMysqlTest.class);

    /*
     * Ordinarily you wouldn't try and run multiple containers simultaneously - this is just used for testing.
     * To avoid memory issues with the default, low memory, docker machine setup, we instantiate only one container
     * at a time, inside the test methods themselves.
     */
    /*
    @ClassRule
    public static MySQLContainer<?> mysql = new MySQLContainer<>(MYSQL_IMAGE);

    @ClassRule
    public static MySQLContainer<?> mysqlOldVersion = new MySQLContainer<>(DockerImageName.parse("mysql:5.5");)

    @ClassRule
    public static MySQLContainer<?> mysqlCustomConfig = new MySQLContainer<>(DockerImageName.parse("mysql:5.6"))
                                                            .withConfigurationOverride("somepath/mysql_conf_override");
    */

    @Test
    public void testSimple() throws SQLException {
        try (
            MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                //            .withConfigurationOverride("somepath/mysql_conf_override")
                .withLogConsumer(new Slf4jLogConsumer(logger))
                .withUsername("user")
                .withPassword("secret")
                .withDatabaseName("humansinlearning")
                .withInitScript("local/userDao.sql")) {

            mysql.start();
            System.out.println(mysql.getJdbcUrl());
            ResultSet resultSet = performQuery(mysql, "select count(*) from humansinlearning.users");
            int resultSetInt = resultSet.getInt(1);

            assertEquals("A basic SELECT query succeeds", 2, resultSetInt);
        }
    }





}
