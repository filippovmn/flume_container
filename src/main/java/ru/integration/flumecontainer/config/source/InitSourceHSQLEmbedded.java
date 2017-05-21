package ru.integration.flumecontainer.config.source;

import org.apache.log4j.Logger;
import ru.integration.flumecontainer.unit.Unit;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by semya on 15.05.2017.
 */
public class InitSourceHSQLEmbedded implements InitSource {
    Logger logger= Logger.getLogger(InitSourceHSQLEmbedded.class);
    String connectionString;

    Connection connection;

    InitSourceHSQLEmbedded(){
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
        }catch (SQLException e) {
            logger.error("Error while init connection ",e);
        }catch (ClassNotFoundException e) {
            logger.error("Error while init connection ",e);
        }
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
        return connectionString;
    }

    public void setSourceInitializer(Object initializer) {
        try {
            this.connectionString = (String) initializer;
        }catch(ClassCastException ex){

            logger.error(String.format(
                    "Initializer must be a String, found: %s", initializer.getClass().toString()),ex);
            throw new IllegalArgumentException(String.format(
                    "Initializer must be a String, found: %s", initializer.getClass().toString()),ex);
        }
    }

    public void init() {

    }
}
