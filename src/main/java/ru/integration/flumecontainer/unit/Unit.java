package ru.integration.flumecontainer.unit;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Created by semya on 21.05.2017.
 */
public interface Unit extends Serializable {

    public Properties getProperties();

    public void setProperties(Properties properties);

    public String getUnitName();

    public void setUnitName(String unitName);

    public List<String> getTags();

    public void setTags(List<String> tags);

    public void start();

    public void stop();

    public void reset();

    public UnitImpl.Status getStatus();
}
