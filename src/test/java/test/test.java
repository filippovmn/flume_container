package test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.integration.flumecontainer.AppConfig;
import ru.integration.flumecontainer.Container;

/**
 * Created by semya on 11.05.2017.
 */
public class test {

    public static void main(String [ ] args) {
        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        String[] beanNames = ctx.getBeanDefinitionNames();

        for (String beanName : beanNames) {

            System.out.println(beanName + " : " + ctx.getBean(beanName).getClass().toString());
        }
        Container container= (Container) ctx.getBean("commonContainer");
        if(container!=null) {
            System.out.println("start");
            container.init();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            container.startAll();

            container.stopAll();
        }else {
            System.out.println("nothing");
        }

    }
}
