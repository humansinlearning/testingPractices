package mysql.embedded;

import com.mysql.cj.jdbc.MysqlDataSource;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.DownloadConfig;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;

import org.apache.commons.lang3.StringUtils;
import org.junit.rules.ExternalResource;

import java.net.URI;

import javax.sql.DataSource;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.DownloadConfig.aDownloadConfig;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.config.ProxyFactory.aHttpProxy;

public class EmbeddedMysqlResource extends ExternalResource {

    private static final String DEFAULT_USER = "foo";
    private static final String DEFAULT_PASSWORD = "bar";
    // TODO use MySQL 8
    // In order to match the MySQL version we're currently using in our DBs, we should use MySQL 8, but there appears to be some sort of bug that prevents it to
    // be used on a CentOS-based Docker image, complaining about not finding the libssl.so.1.0.0 shared library (which actually comes with the MySQL package).
    // This appears to be an issue with Wix Embedded MySQL.
    private static final MysqldConfig CONFIG = aMysqldConfig(Version.v5_7_27)
        // make sure the server only listens on local ports
        .withServerVariable("bind-address", "localhost")
        // The following variables are only available in MySQL 8
        // .withServerVariable("admin-address", "localhost")
        // disable the X Protocol, again to disable opening a public port
        // .withServerVariable("mysqlx", "OFF")
        // disable SSL
        // .withServerVariable("ssl", "OFF")
        .withUser(DEFAULT_USER, DEFAULT_PASSWORD)
        .build();

    private final String schema;
    private final String initScriptResourcePath;

    private EmbeddedMysql mysql;
    private MysqlDataSource dataSource;

    public EmbeddedMysqlResource(String schema, String initScriptResourcePath) {
        this.schema = schema;
        this.initScriptResourcePath = initScriptResourcePath;
    }

    public DataSource getDataSource() {
        if (dataSource == null) {
            throw new IllegalStateException("Embedded MySql not initialised");
        }
        return dataSource;
    }

    @Override
    public void before() {
        mysql = anEmbeddedMysql(CONFIG)
            .withDownloadConfig(makeDownloadConfig())
            .addSchema(schema, ScriptResolver.classPathScript(initScriptResourcePath))
            .start();

        dataSource = new MysqlDataSource();
        dataSource.setServerName("localhost");
        dataSource.setPort(3310);
        dataSource.setUser(DEFAULT_USER);
        dataSource.setPassword(DEFAULT_PASSWORD);
        dataSource.setDatabaseName(schema);
    }

    private DownloadConfig makeDownloadConfig() {
        DownloadConfig.Builder builder = aDownloadConfig();
        String proxyUrl = System.getenv("https_proxy");
        if (StringUtils.isEmpty(proxyUrl)) {
            proxyUrl = System.getenv("HTTPS_PROXY");
        }
        if (StringUtils.isNotEmpty(proxyUrl)) {
            URI uri = URI.create(proxyUrl);
            builder.withProxy(aHttpProxy(uri.getHost(), uri.getPort()));
        }
        return builder.build();
    }

    @Override
    public void after() {
        if (mysql != null) {
            mysql.stop();
        }
    }


}
