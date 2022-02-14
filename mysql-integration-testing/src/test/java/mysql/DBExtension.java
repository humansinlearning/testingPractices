package mysql;

import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

public class DBExtension implements BeforeAllCallback {


    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        try (
            MySQLContainer<?> mysql = new MySQLContainer<>(DockerImageName.parse("mysql:5.5"))
                .withUsername("user")
                .withPassword("secret")
                .withDatabaseName("humansinlearning")) {

            mysql.start();
        }

    }
}
