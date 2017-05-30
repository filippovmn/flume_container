package ru.integration.flumecontainer.config.source;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.log4j.Logger;
import org.hsqldb.cmdline.SqlFile;
import org.hsqldb.cmdline.SqlToolError;
import ru.integration.flumecontainer.config.source.jdbc.DatabasePrepare;
import ru.integration.flumecontainer.unit.Unit;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by semya on 15.05.2017.
 */
public class InitSourceJDBC implements InitSource {

    Logger logger= Logger.getLogger(InitSourceJDBC.class);

    private HashMap<String,Unit> source = new HashMap();

    private Properties initializer;

    private DataSource dataSource;

    private String driverName;
    //"jdbc:h2:~/test"
    private String uri;
    //"test"
    private String login;

    private String password;

    private String shema_sql;


    public Properties getProperties(String agent) {
        return null;
    }

    public Unit getUnit(String unitName) {
        return null;
    }

    public Map<String, Unit> getUnits() {
        return null;
    }

    public void addUnit(String unitName, Properties properties) {

    }

    public void addUnit(String unitName, Properties properties, List<String> tags) {

    }

    public void deleteUnit(String unitName) {

    }

    public Object getSourceInitializer() {
        return this.initializer;
    }

    public void setSourceInitializer(Properties initializer) {
            this.initializer=initializer;
    }

    public void init() {
        this.uri = initializer.getProperty("initsourcejdbc.connection_string");
        this.login = initializer.getProperty("initsourcejdbc.login");
        this.password = initializer.getProperty("initsourcejdbc.password");
        this.shema_sql = initializer.getProperty("initsourcejdbc.shema_sql");
        //this.driverName = initializer.getProperty("initsourcejdbc.drivername");
        this.initDataSource(this.uri, this.login, this.password);
        DatabasePrepare dbPrepare=new DatabasePrepare(this.dataSource,this.shema_sql);
        dbPrepare.prepare();
    }

    public void initDataSource(String uri,String  login,String password){
        try {

            ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(uri, login, password);
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

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SqlToolError sqlToolError) {
            sqlToolError.printStackTrace();
        }
    }
}
