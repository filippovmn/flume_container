package ru.integration.prj;


import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */
public interface Container {

    public boolean init();

    public void start();

    public void stop();

    public void addAgent(String agent, Properties properties);
}
