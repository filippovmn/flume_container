package ru.integration.flumecontainer;


import ru.integration.flumecontainer.config.source.InitSource;

import java.util.List;
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

    public void setSource(InitSource source);

    public List<String> getAllAgentNames();
}
