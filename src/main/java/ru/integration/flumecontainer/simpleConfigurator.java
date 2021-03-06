package ru.integration.flumecontainer;

import org.apache.flume.conf.FlumeConfiguration;
import org.apache.flume.node.AbstractConfigurationProvider;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */
public class simpleConfigurator extends AbstractConfigurationProvider {

    private static final Logger LOGGER = Logger
            .getLogger(simpleConfigurator.class);

    Properties properties;

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public simpleConfigurator(String agentName, Properties properties) {
        super(agentName);
        this.properties = properties;
    }
    @Override
    protected FlumeConfiguration getFlumeConfiguration() {
        return new FlumeConfiguration(super.toMap(properties));
    }
}
