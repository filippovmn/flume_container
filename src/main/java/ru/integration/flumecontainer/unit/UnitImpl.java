package ru.integration.flumecontainer.unit;

import org.apache.flume.node.AbstractConfigurationProvider;
import org.apache.flume.node.Application;
import org.apache.log4j.Logger;
import ru.integration.flumecontainer.simpleConfigurator;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by semya on 17.05.2017.
 */
public class UnitImpl implements Unit {

    Logger logger= Logger.getLogger(UnitImpl.class);

    String unitName;
    Properties properties;
    Application unit=new Application();
    List<String> tags =new ArrayList<String>();
    Status status=Status.NEW;


    public UnitImpl(String unitName) {
        this.unitName=unitName;
    }

    public UnitImpl(String unitName, Properties properties) {
        this.unitName=unitName;
        this.properties=properties;
        status=Status.READY;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
        status=Status.READY;
    }

    public String getUnitName() {
        return unitName;
    }
    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public List<String> getTags() {
        return this.tags;
    }

    public void setTags(List<String> tags) {
        this.tags =tags;
    }

    public void start() {
       try {
           System.out.println("Start: "+unitName);
           if(status==Status.READY||status==Status.STOPPED) {
               AbstractConfigurationProvider provider = new simpleConfigurator(unitName,properties);
               unit.handleConfigurationEvent(provider.getConfiguration());
               this.unit.start();
               status=Status.STARTED;
               System.out.println("Started: "+unitName);
           }else{
               throw new IllegalStateException(String.format(
                       "Status must be READY or STOPPED but %s",status ));
           }
       }catch (IllegalStateException e){
           System.out.println("error1: "+unitName + " "+e);
           status=Status.FAULT;
       }
       catch(Exception e){
           System.out.println("error2: "+unitName + " "+e);
           status=Status.FAULT;
       }
    }

    public void stop() {
        try {
            if(status==Status.STARTED) {
                this.unit.stop();
                status=Status.STOPPED;
            }else{
                throw new IllegalStateException(String.format(
                        "Status must be STARTED but %s",status ));
            }
        }catch (IllegalStateException e){

        }
        catch(Exception e){
            status=Status.FAULT;
        }
    }

    public void reset() {
        try {
            if(status==Status.FAULT) {
                status=Status.READY;
            }else{
                throw new IllegalStateException(String.format(
                        "Status must be FAULT but %s",status ));
            }
        }catch (IllegalStateException e){
        }
    }

    public Status getStatus() {
        return this.status;
    }

    @Override
    public String toString() {
        return "FlumeAgent{" +
                "agent='" + unitName +
                '}';
    }
    public enum  Status{
            NEW,
            READY,
            STARTED,
            STOPPED,
            FAULT
    };
}
