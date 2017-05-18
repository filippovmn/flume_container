package ru.integration.flumecontainer.config.source;

import org.apache.log4j.Logger;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by mfilippov on 2017-04-18.
 */
public class InitSourceFileList implements InitSource {
    Logger logger= Logger.getLogger(InitSourceFileList.class);


    private List<String> files;

    HashMap<String,Properties> source=new HashMap();
/*
    {
        files= new ArrayList<String>();
        files.add("src/main/resources/my-agent.properties");
    }
*/

    public Properties getProperties(String agent){
        Properties properties=source.get(agent);
        System.out.println(properties.toString());
        return properties;
    }

    public List<String> getAgents(){
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

    public Object getSourceInitializer() {
        return files;
    }

    public void setSourceInitializer(Object initializer) {
        try {
            this.files = (List<String>) initializer;
        }catch(ClassCastException ex){

            logger.error(String.format(
                    "Initializer must be a List<String>, found: %s", initializer.getClass().toString()),ex);
            throw new IllegalArgumentException(String.format(
                    "Initializer must be a List<String>, found: %s", initializer.getClass().toString()),ex);
        }
    }

    public void init() {
        if(files!=null) {
            for (String file : files) {
                try {
                    Properties props = new Properties();
                    props.load(new FileInputStream(file));
                    String[] tree = file.split("[.]")[0].split("[/]");
                    source.put(tree[tree.length - 1], props);
                } catch (IOException e) {
                    logger.error("error " + e.toString());
                }
            }
        }else{
            logger.warn("source is empty!");
        }
    }
}
