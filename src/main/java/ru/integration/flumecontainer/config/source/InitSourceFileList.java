package ru.integration.flumecontainer.config.source;

import org.apache.log4j.Logger;
import ru.integration.flumecontainer.unit.Unit;
import ru.integration.flumecontainer.unit.UnitImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mfilippov on 2017-04-18.
 */
public class InitSourceFileList implements InitSource {
    Logger logger= Logger.getLogger(InitSourceFileList.class);


    private List<String> files = new ArrayList<String>();

    HashMap<String,Unit> source=new HashMap();

    Properties initializer=null;
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
        return initializer;
    }

    public void setSourceInitializer(Properties initializer) {
        this.initializer=initializer;
    }

    public void init() {
        try {
            String directory = initializer.getProperty("flume.source.dir", null);
            if (directory==null){
                throw new CouldNotFindPropertyException("Directory is not set");
            }
            String propPattern = initializer.getProperty("flume.source.conf-pattern", null);
            if(propPattern==null){
                logger.info("File pattern is not set!");
            }
            Pattern pattern = Pattern.compile(propPattern);
            File dir = new File(directory);
            if (dir.isDirectory()) {
                logger.info("Search in dir:"+dir.getName());
                for (File file : dir.listFiles()) {
                    String name = file.getName();
                    Matcher matcher = pattern.matcher(name);
                    if (matcher.matches()) {
                        logger.info("found "+file.getName());
                        this.files.add(file.getAbsolutePath());
                    }
                }
            }else{
                logger.info("Not a directory: "+directory);
            }
            if (files != null&&files.size()>0) {
                for (String file : files) {
                    try {
                        logger.info("Initialize file " + file);
                        Properties props = new Properties();
                        props.load(new FileInputStream(file));
                        String filname = new File(file).getName().split("\\.")[0];
                        source.put(filname, new UnitImpl(filname,props));
                    } catch (IOException e) {
                        logger.error("error " + e.toString());
                    }
                }
            } else {
                logger.warn("Source is empty!");
            }
        } catch (Exception ex) {
            logger.error("Initialization failed ", ex);
        }
    }
}
