package ru.integration.prj.config.source;

import java.util.List;
import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */
public interface initSource {
    public Properties getProperties(String agent);

    public List<String> getAllAgents();

    public void addAgent(String agent,Properties properties);

    public void deleteAgent(String agent);
}
