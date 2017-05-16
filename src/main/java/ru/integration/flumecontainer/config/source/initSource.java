package ru.integration.flumecontainer.config.source;

import java.util.List;
import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */
public interface InitSource {
    public Properties getProperties(String agent);

    public List<String> getAgents();

    public void addAgent(String agent,Properties properties);

    public void deleteAgent(String agent);

    public Object getSourceInitializer();

    public void setSourceInitializer(Object initializer);

    public void init();
}
