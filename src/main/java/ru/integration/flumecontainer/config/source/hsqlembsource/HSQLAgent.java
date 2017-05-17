package ru.integration.flumecontainer.config.source.hsqlembsource;

import java.util.Properties;

/**
 * Created by semya on 17.05.2017.
 */
public class HSQLAgent {

    String agent;
    Properties properties;

    public Properties getProperties() {
        return properties;
    }
    public void setProperties(Properties properties) {
        this.properties = properties;
    }
    public String getAgent() {
        return agent;
    }
    public void setAgent(String agent) {
        this.agent = agent;
    }
    @Override
    public String toString() {
        return "FlumeAgent{" +
                "agent='" + agent +
                '}';
    }
}
