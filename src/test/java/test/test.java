package test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
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
        Container container= (Container) ctx.getBean("container");
        if(container!=null) {
            System.out.println("start");
            container.init();
            try {
                container.startAll();
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            container.destroy();
        }else {
            System.out.println("nothing");
        }

    }
}
