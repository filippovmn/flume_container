package ru.integration.flumecontainer.config.source;

import org.apache.log4j.Logger;
import ru.integration.flumecontainer.unit.Unit;
import ru.integration.flumecontainer.unit.UnitImpl;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by mfilippov on 2017-04-18.
 */
public class InitSourceFileList implements InitSource {
    Logger logger= Logger.getLogger(InitSourceFileList.class);


    private List<String> files;

    HashMap<String,Unit> source=new HashMap();
/*
    {
        files= new ArrayList<String>();
        files.add("src/main/resources/my-agent.properties");
    }
*/

    public Unit getUnit(String unitName){
        Unit unit=source.get(unitName);
        return unit;
    }

    public Map<String,Unit> getUnits(){
        return this.source;
    }

    public void addUnit(String unitName, Properties properties){
        Unit unit=new UnitImpl(unitName,properties);
        source.put(unitName,unit);
    }

    public void addUnit(String unitName, Properties properties, List<String> tags) {
        Unit unit=new UnitImpl(unitName,properties);
        unit.setTags(tags);
        source.put(unitName,unit);
    }

    public void deleteUnit(String unitName){
        Unit unit=source.get(unitName);
        if(unit.getStatus()== UnitImpl.Status.STARTED){
            unit.stop();
        }
        source.remove(unitName);
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
                    source.put(tree[tree.length - 1],new UnitImpl(tree[tree.length - 1],
                                                                  props));
                } catch (IOException e) {
                    logger.error("error " + e.toString());
                }
            }
        }else{
            logger.warn("source is empty!");
        }
    }
}
