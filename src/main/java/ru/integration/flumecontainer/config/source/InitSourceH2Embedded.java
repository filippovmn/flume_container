package ru.integration.flumecontainer.config.source;

import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by semya on 15.05.2017.
 */
public class InitSourceH2Embedded implements InitSource {

    Connection connection;

    InitSourceH2Embedded(){
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "test", "" );
        }catch (SQLException e) {
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public Properties getProperties(String agent) {
        return null;
    }

    public List<String> getAgents() {
        return null;
    }

    public void addAgent(String agent, Properties properties) {

    }

    public void deleteAgent(String agent) {

    }

    public Object getSourceInitializer() {
        return null;
    }

    public void setSourceInitializer(Object initializer) {

    }

    public void init() {

    }
}
