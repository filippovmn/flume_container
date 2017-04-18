package ru.integration.prj.config.source;

import org.apache.flume.node.Application;

import java.util.*;

/**
 * Created by mfilippov on 2017-04-18.
 */
public class initSourceInMemory {

    HashMap<String,Properties> source;
    {
        Properties props=new Properties();
        //props.put();
        source.put("agent",props);
    }
    public Properties getProperties(String agent){
        Properties properties=source.get(agent);
        return properties;
    }
    public void setProperties(String agent){
    }
    public List<String> getAllAgents(){
        ArrayList<String> agents=new ArrayList<String>();
        for(Map.Entry<String ,Properties> entry:source.entrySet()){
            agents.add(entry.getKey());
        }
        return agents;
    }
    public void deleteAgent(String agent){
        source.remove(agent);
    }
}
