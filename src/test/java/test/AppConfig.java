package test;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.hsqldb.cmdline.SqlToolError;
import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.integration.flumecontainer.CommonContainer;
import ru.integration.flumecontainer.Container;
import ru.integration.flumecontainer.config.source.InitSource;
import ru.integration.flumecontainer.config.source.InitSourceFileList;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.hsqldb.cmdline.SqlFile;

/**
 * Created by mfilippov on 2017-04-18.
 */

@Configuration
public class AppConfig {

    /*@Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        //builder.setName("testdb");
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL) //.H2 or .DERBY
                .addScript("sql_init/confRegistry_mysql_create.sql")
                .addScript("sql_init/insert-data.sql")
                .build();
        return db;
    }*/
    @Bean
    public DataSource dataSource(){
        PoolingDataSource<PoolableConnection> dataSource=null;
        try {

            ConnectionFactory connectionFactory = new DriverManagerConnectionFactory("jdbc:hsqldb:file:testdb;ifexists=true", "SA", "");
            PoolableConnectionFactory poolableConnectionFactory =
                    new PoolableConnectionFactory(connectionFactory, null);
            ObjectPool<PoolableConnection> connectionPool =
                    new GenericObjectPool<PoolableConnection>(poolableConnectionFactory);
            poolableConnectionFactory.setPool(connectionPool);
            dataSource = new PoolingDataSource<PoolableConnection>(connectionPool);

            SqlFile sqlFile=new SqlFile(new File("src/test/resources/sql_init/confRegistry_mysql_create.sql"));
            sqlFile.setConnection(dataSource.getConnection());
            sqlFile.setAutoClose(true);
            sqlFile.execute();
            return dataSource;

        } catch (SQLException e) {
              e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SqlToolError sqlToolError) {
            sqlToolError.printStackTrace();
        }
        return dataSource;
    }

    @Bean
    InitSource initSource(){
        Properties initializer=new Properties();
        initializer.put("flume.source.dir","src/test/resources");
        initializer.put("flume.source.conf-pattern",".*\\.conf");
        InitSource source = new InitSourceFileList();
        source.setSourceInitializer(initializer);
        return  source;
    }

    @Bean
    Container container(){
        Container container= new CommonContainer();
        container.setSource(initSource());
        return container;
    }
    @PostConstruct
    public void startDBManager() {
        DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:file:testdb", "--user", "sa", "--password", "" });

    }
}
