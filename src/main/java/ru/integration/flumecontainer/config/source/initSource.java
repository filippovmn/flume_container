package ru.integration.flumecontainer.config.source;

import ru.integration.flumecontainer.unit.Unit;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */

public interface InitSource {

    public Unit getUnit(String unitName);

    public Map<String,Unit> getUnits();

    public void addUnit(String unitName, Properties properties);

    public void addUnit(String unitName, Properties properties, List<String> tags);

    public void deleteUnit(String unitName);

    public Object getSourceInitializer();

    public void setSourceInitializer(Properties initializer);

    public void init();
}
