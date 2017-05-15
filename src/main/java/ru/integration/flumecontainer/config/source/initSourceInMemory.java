package ru.integration.flumecontainer.config.source;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by mfilippov on 2017-04-18.
 */
@Component
public class initSourceInMemory {

    HashMap<String,Properties> source=new HashMap();

    {
        try {
            Properties props=new Properties();
            props.load(new FileInputStream("src/main/resources/test.properties"));
            source.put("my-agent",props);
        } catch (IOException e) {
            System.out.println("error "+e.toString());
        }
    }
    public Properties getProperties(String agent){
        Properties properties=source.get(agent);
        System.out.println(properties.toString());
        return properties;
    }

    public List<String> getAllAgents(){
        ArrayList<String> agents=new ArrayList<String>();
        for(Map.Entry<String ,Properties> entry:source.entrySet()){
            System.out.println("agent: "+entry.getKey());
            agents.add(entry.getKey());
        }
        return agents;
    }

    public void addAgent(String agent,Properties properties){
        source.put(agent,properties);
    }

    public void deleteAgent(String agent){
        source.remove(agent);
    }
}
