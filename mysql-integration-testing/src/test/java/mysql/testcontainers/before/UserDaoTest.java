package mysql.testcontainers.before;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import org.junit.Assert;
import org.junit.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import mysql.UserDao;

public class UserDaoTest {

    @Test
    public void createUser() throws SQLException {
        try (
            MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
                .withUsername("user")
                .withPassword("secret")
                .withDatabaseName("humansinlearning")
                .withInitScript("local/userDao.sql")) {

            mysql.start();
            System.out.println(mysql.getJdbcUrl());

            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(mysql.getJdbcUrl());
            hikariConfig.setUsername(mysql.getUsername());
            hikariConfig.setPassword(mysql.getPassword());
            hikariConfig.setDriverClassName(mysql.getDriverClassName());

            UserDao user = new UserDao("andrei", "con", "andrei.con");
            user.dbUrl = mysql.getJdbcUrl();
            user.dbUser = mysql.getUsername();
            user.dbPwd = mysql.getPassword();

            Assert.assertFalse(user.createUser(user));

            DataSource ds = new HikariDataSource(hikariConfig);
            Statement statement = ds.getConnection().createStatement();
            statement.execute("select count(*) from users");
            ResultSet resultSet = statement.getResultSet();
            resultSet.next();

            Assert.assertEquals("Rows count should be equal: ", 3, resultSet.getInt(1));
        }
    }
}