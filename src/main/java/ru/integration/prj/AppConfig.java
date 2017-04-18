package ru.integration.prj;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Created by mfilippov on 2017-04-18.
 */

@Configuration
public class AppConfig {
    @Autowired
    public Container commonContainer;



}
