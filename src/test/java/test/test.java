package test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.integration.flumecontainer.Container;

import java.io.File;

/**
 * Created by semya on 11.05.2017.
 */
public class test {

    public static void main(String [ ] args) {
        File file=new File("src/test/resources/sql_init");
        for(File child:file.listFiles())
        System.out.println(child.getName());

        AnnotationConfigApplicationContext ctx =
                new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        Container container= (Container) ctx.getBean("container");
        if(container!=null) {
            System.out.println("start");
            container.init();
            container.destroy();
            return;
            /*try {
                container.startAll();
                Thread.sleep(1);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            container.destroy();*/
        }else {
            System.out.println("nothing");
        }

    }
}
