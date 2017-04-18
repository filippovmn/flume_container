package ru.integration.prj;

import org.apache.flume.agent.embedded.EmbeddedAgent;
import org.apache.flume.agent.embedded.EmbeddedAgentConfiguration;
import org.apache.flume.node.Application;


import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by mfilippov on 2017-04-13.
 */
public class Container {

    List<Application> agents;

    public boolean init(List<String> properties){
        try{
            agents=new ArrayList<Application>();
            Application app=new Application();
            return true;
        }catch(Exception ex){
            return false;
        }
    }
    public void start(){

    }

    public void stop(){
    }

}
