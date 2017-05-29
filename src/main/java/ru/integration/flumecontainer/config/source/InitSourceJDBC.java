package ru.integration.flumecontainer.config.source;

import org.apache.log4j.Logger;
import ru.integration.flumecontainer.config.source.jdbc.DatabasePrepare;
import ru.integration.flumecontainer.unit.Unit;

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

    HashMap<String,Unit> source = new HashMap();

    Properties initializer;

    String driverName;
    //"jdbc:h2:~/test"
    String connectionString;
    //"test"
    String login;

    String password;

    Connection connection;

    private void InitConnection(){
        try {
            Class.forName(this.driverName);
            this.connection = DriverManager.getConnection(this.connectionString, this.login, this.password );
        }catch (SQLException e) {
            logger.error("Error while init connection ",e);
        }catch (ClassNotFoundException e) {
            logger.error("Error while init connection ",e);
        }
    }
    private void InitDB(){

    }


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
        this.connectionString = initializer.getProperty("initsourcejdbc.connection_string");
        this.login = initializer.getProperty("initsourcejdbc.login");
        this.password = initializer.getProperty("initsourcejdbc.password");
        this.driverName = initializer.getProperty("initsourcejdbc.drivername");
        this.InitConnection();
        DatabasePrepare dbPrepare=new DatabasePrepare(this.connection);
    }
}
