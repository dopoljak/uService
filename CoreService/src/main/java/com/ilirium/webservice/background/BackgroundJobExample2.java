package com.ilirium.webservice.background;


/*
import com.ilirium.database.flyway.SchemaVersion;
import com.ilirium.database.flyway.SchemaVersionRepository;
import org.jboss.weld.context.RequestContext;
import org.jboss.weld.context.unbound.UnboundLiteral;
*/

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 *  !!!! work-in-progress - example !!!!
 */
@ApplicationScoped
public class BackgroundJobExample2 {

    /*
    @Inject
    private Instance<Ping> pingInstance;
*/

    @PostConstruct
    public void post() {


        System.out.println(">> ExampleBackThread post construct ....");
        System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println("<< ExampleBackThread post construct ....");

        beepForAnHour();
    }

    public void print() {
        System.out.println("print ....");
    }


    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void beepForAnHour() {
/*

        final ScheduledFuture<?> beeperHandle = scheduler.scheduleAtFixedRate(pingInstance.get(), 3, 3, TimeUnit.SECONDS);
        scheduler.schedule(new Runnable() {
            public void run() {
                beeperHandle.cancel(true);
            }
        }, 60 * 60, TimeUnit.SECONDS);
*/

    }
}

/*
class Ping implements Runnable {

    @Inject
    private SchemaVersionRepository schemaVersionRepository;

    @Override
    public void run() {
        RequestContext requestContext = CDI.current().select(RequestContext.class, UnboundLiteral.INSTANCE).get();
        try {
            requestContext.activate();

            System.out.println("########## beep : " + System.currentTimeMillis());
            List<SchemaVersion> endUserCoollection = schemaVersionRepository.getSchemaVersions();
            for (SchemaVersion endUser : endUserCoollection) {
                System.out.println("" + endUser.getVersion());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            requestContext.deactivate();
        }
    }
}*/