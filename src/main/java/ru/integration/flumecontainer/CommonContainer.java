package ru.integration.flumecontainer;

import org.apache.log4j.Logger;
import ru.integration.flumecontainer.config.source.InitSource;
import ru.integration.flumecontainer.unit.Unit;
import ru.integration.flumecontainer.unit.UnitImpl;


import java.util.*;


/**
 * Created by mfilippov on 2017-04-13.
 */
public class CommonContainer implements Container {

    Logger logger= Logger.getLogger(CommonContainer.class);

    InitSource source;

    public boolean init(){
        try{
            return true;
        }catch(Exception ex){
            logger.error("error in init: "+ex.toString());
            return false;
        }
    };
    public void startAll(){
        for(Map.Entry<String ,Unit> entry:source.getUnits().entrySet()){
            try {
            entry.getValue().start();
            }catch (Exception e){
                System.out.println("error while starting: "+e.toString());
            }
        }
    };

    public void stopAll(){
        for(Map.Entry<String ,Unit> entry:source.getUnits().entrySet()){
            try {
                entry.getValue().stop();
            }catch (Exception e){
                System.out.println("error while stopping: "+e.toString());
            }
        }
    };

    public UnitImpl.Status startUnit(String unitName){
        source.getUnit(unitName).start();
        return source.getUnit(unitName).getStatus();
    }

    public UnitImpl.Status stopUnit(String unitName) {
        source.getUnit(unitName).stop();
        return source.getUnit(unitName).getStatus();
    }

    public void addUnit(String unit, Properties properties){
        try {
            source.addUnit(unit, properties);
        }catch (Exception ex){
            logger.error(String.format("error while initialize new flume agent %s ", unit),ex);
        }
    };

    public void deleteUnit(String unit){
        try {
            source.getUnit(unit).stop();
            source.deleteUnit(unit);
        }catch (Exception ex){
            logger.error(String.format("error while delete flume agent %s ", unit),ex);
        }
    }

    public void setSource(InitSource source) {
        this.source=source;
    }

    public Set<String> getAllUnitsNames(){
        return source.getUnits().keySet();
    }

    public Map<String, UnitImpl.Status> getUnitsInfo() {
        Map<String, UnitImpl.Status> info=new HashMap<String, UnitImpl.Status>() ;
        for(String unitName:source.getUnits().keySet()){
            info.put(unitName,source.getUnit(unitName).getStatus());
        }
        return info;
    }


}
