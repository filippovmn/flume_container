package ru.integration.prj;


import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */
public interface Container {

    public boolean init();

    public void startAll();

    public void stopAll();

    public void addAgent(String agent, Properties properties);

    public void deleteAgent(String agent);
}
