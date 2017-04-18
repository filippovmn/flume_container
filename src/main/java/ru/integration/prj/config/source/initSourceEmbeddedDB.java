package ru.integration.prj.config.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */
public class initSourceEmbeddedDB {

    public Properties getProperties(String agent){
        return new Properties();
    }
    public void setProperties(String agent){
    }
    public List<String> getAllAgents(){
        return new ArrayList<String>();
    }
    public void deleteAgent(String agent){

    }
}
