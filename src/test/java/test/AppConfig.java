package test;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.integration.flumecontainer.CommonContainer;
import ru.integration.flumecontainer.Container;
import ru.integration.flumecontainer.config.source.InitSource;
import ru.integration.flumecontainer.config.source.InitSourceFileList;
import java.util.Properties;

/**
 * Created by mfilippov on 2017-04-18.
 */

@Configuration
public class AppConfig {

    @Bean
    InitSource initSource(){
        Properties initializer=new Properties();
        initializer.put("flume.source.dir","src/main/resources");
        initializer.put("flume.source.conf-pattern",".*\\.conf");
        InitSource source = new InitSourceFileList();
        source.setSourceInitializer(initializer);
        return  source;
    }

    @Bean
    Container container(){
        Container container= new CommonContainer();
        container.setSource(initSource());
        return container;
    }
}
