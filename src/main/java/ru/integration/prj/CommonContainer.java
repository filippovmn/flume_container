package ru.integration.prj;

import org.apache.flume.node.AbstractConfigurationProvider;
import org.apache.flume.node.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.integration.prj.config.source.initSourceEmbeddedDB;


import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by mfilippov on 2017-04-13.
 */
@Service
public class CommonContainer {

    HashMap<String,Application> agents;

    @Autowired
    initSourceEmbeddedDB source;
    @Autowired
    AbstractConfigurationProvider provider;

    public boolean init(){
        try{
            List<String> agents=source.getAllAgents();
            for(String agent:agents) {
                Application app = new Application();
                app.handleConfigurationEvent(provider.getConfiguration());
            }
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    public void start(){
        for(Map.Entry<String ,Application> entry:agents.entrySet()){
            entry.getValue().start();
        }
    }

    public void stop(){
        for(Map.Entry<String ,Application> entry:agents.entrySet()){
            entry.getValue().stop();
        }
    }

}
