package test;

import org.hsqldb.util.DatabaseManagerSwing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.integration.flumecontainer.CommonContainer;
import ru.integration.flumecontainer.Container;
import ru.integration.flumecontainer.config.source.InitSource;
import ru.integration.flumecontainer.config.source.InitSourceFileList;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
    InitSource initSource(){
        try {
            Connection c = DriverManager.getConnection("jdbc:hsqldb:file:testdb", "SA", "");
            Statement stmt=c.createStatement();
            FileReader reader =new FileReader("src/test/resources/sql_init/confRegistry_mysql_create.sql");
            StringBuilder builder= new StringBuilder();
            char[] buffer = new char[100];
            while(reader.read(buffer)!=-1)
                builder.append(buffer);
            reader.close();
            System.out.println(builder.toString());
            //stmt.execute(builder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--user", "sa", "--password", "" });

    }
}
