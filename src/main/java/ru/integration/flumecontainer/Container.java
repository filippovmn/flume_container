package ru.integration.flumecontainer;


import ru.integration.flumecontainer.config.source.InitSource;
import ru.integration.flumecontainer.unit.UnitImpl;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * Created by mfilippov on 2017-04-18.
 */
public interface Container {

    public boolean init();

    public boolean destroy();

    public void startAll();

    public void stopAll();

    public UnitImpl.Status startUnit(String unitName);

    public UnitImpl.Status stopUnit(String unitName);

    public void addUnit(String unit, Properties properties);

    public void deleteUnit(String unit);

    public void setSource(InitSource source);

    public Set<String> getAllUnitsNames();

    public Map<String,UnitImpl.Status> getUnitsInfo();
}
