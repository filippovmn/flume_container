package ru.integration.prj;

import org.apache.flume.node.AbstractConfigurationProvider;
import org.apache.flume.node.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.integration.prj.config.source.initSourceInMemory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by mfilippov on 2017-04-13.
 */
@Component
public class CommonContainer {

    HashMap<String,Application> agents;

    @Autowired
    initSourceInMemory source;

    public boolean init(){
        try{
            List<String> agentsNames=source.getAllAgents();
            for(String agent:agentsNames) {
                Application app = new Application();
                Properties properties=source.getProperties(agent);
                AbstractConfigurationProvider provider = new simpleConfigurator(agent,properties);
                app.handleConfigurationEvent(provider.getConfiguration());
                agents.put(agent,app);
            }
            return true;
        }catch(Exception ex){
            return false;
        }
    };
    public void startAll(){
        for(Map.Entry<String ,Application> entry:agents.entrySet()){
            entry.getValue().start();
        }
    };

    public void stopAll(){
        for(Map.Entry<String ,Application> entry:agents.entrySet()){
            entry.getValue().stop();
        }
    };

    public void addAgent(String agent, Properties properties){
        Application app = new Application();
        AbstractConfigurationProvider provider = new simpleConfigurator(agent,properties);
        app.handleConfigurationEvent(provider.getConfiguration());
        agents.put(agent,app);
        source.addAgent(agent,properties);
    };

    public void deleteAgent(String agent){
        agents.get(agent).stop();
        agents.remove(agent);
        source.deleteAgent(agent);
    }
}
