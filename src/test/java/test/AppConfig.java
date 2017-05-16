package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.integration.flumecontainer.CommonContainer;
import ru.integration.flumecontainer.Container;
import ru.integration.flumecontainer.config.source.InitSource;
import ru.integration.flumecontainer.config.source.InitSourceFileList;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * Created by mfilippov on 2017-04-18.
 */

@Configuration
public class AppConfig {

    @Bean
    InitSource initSource(){
        List<String> initializer=asList("src/main/resources/my-agent.properties");
        InitSource source = new InitSourceFileList();
        source.setSourceInitializer(initializer);
        source.init();
        return  source;
    }

    @Bean
    Container container(){
        Container container= new CommonContainer();
        container.setSource(initSource());
        return container;
    }
}
