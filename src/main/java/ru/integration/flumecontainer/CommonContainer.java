package ru.integration.flumecontainer;

import org.apache.flume.node.AbstractConfigurationProvider;
import org.apache.flume.node.Application;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.integration.flumecontainer.config.source.InitSource;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by mfilippov on 2017-04-13.
 */
public class CommonContainer implements Container {

    Logger logger= Logger.getLogger(CommonContainer.class);

    HashMap<String,Application> agents=new HashMap<String, Application>();

    InitSource source;

    public boolean init(){
        try{
            logger.info("start initialize");
            List<String> agentsNames=source.getAgents();
            for(String agent:agentsNames) {
                logger.info(String.format("init %s agent", agent));
                Application app = new Application();
                Properties properties=source.getProperties(agent);
                AbstractConfigurationProvider provider = new simpleConfigurator(agent,properties);
                app.handleConfigurationEvent(provider.getConfiguration());
                logger.info(String.format("%s agent initialized", agent));
                agents.put(agent,app);
            }
            logger.info("end init");
            return true;
        }catch(Exception ex){
            logger.error("error in init: "+ex.toString());
            return false;
        }
    };
    public void startAll(){
        for(Map.Entry<String ,Application> entry:agents.entrySet()){
            try {
            entry.getValue().start();
            }catch (Exception e){
                System.out.println("error while starting: "+e.toString());
            }
        }
    };

    public void stopAll(){
        for(Map.Entry<String ,Application> entry:agents.entrySet()){
            try {
                entry.getValue().stop();
            }catch (Exception e){
                System.out.println("error while stopping: "+e.toString());
            }
        }
    };

    public void addAgent(String agent, Properties properties){
        Application app = new Application();
        try {
            AbstractConfigurationProvider provider = new simpleConfigurator(agent, properties);
            app.handleConfigurationEvent(provider.getConfiguration());
            agents.put(agent, app);
            source.addAgent(agent, properties);
        }catch (Exception ex){
            logger.error(String.format("error while initialize new flume agent %s ", agent),ex);
        }
    };

    public void deleteAgent(String agent){
        try {
            agents.get(agent).stop();
            agents.remove(agent);
            source.deleteAgent(agent);
        }catch (Exception ex){
            logger.error(String.format("error while delete flume agent %s ",agent ),ex);
        }
    }

    public void setSource(InitSource source) {
        this.source=source;
    }

    public List<String> getAllAgentNames(){
        return source.getAgents();
    }
}
