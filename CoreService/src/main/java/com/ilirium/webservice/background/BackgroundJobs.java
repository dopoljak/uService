package com.ilirium.webservice.background;

import com.ilirium.webservice.commons.H2DatabaseBackup;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 *  !!!! work-in-progress - example !!!!
 */
/**
 * @author dpoljak
 */
//@WebListener
@ApplicationScoped
public class BackgroundJobs { //implements ServletContextListener {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BackgroundJobs.class);

    @Inject
    private H2DatabaseBackup h2DatabaseBackup;


    public void init(@Observes @Initialized(ApplicationScoped.class) Object init) {

        LOGGER.info(">> init ...");

        //new Thread(CDI.current().select(GetLatestTradesJob.class).get()).start();

        LOGGER.info("<< init ...");
    }


    public void destroy(@Observes @Destroyed(ApplicationScoped.class) Object init) {
        LOGGER.info(">> destroy ...");


        //LOG.info("Disconnected from the Exchange"));

        LOGGER.info("<< destroy ...");
    }


    private void testJob() {

    /*
    new Thread(new Runnable() {

        @Override
        public void run() {

            while (true) {
                final Job pojo = CDI.current().select(Job.class).get();
                new Thread(pojo).start();
                sleep();
            }
        }

        private void sleep() {
            try {
                System.out.println("Sleeping for 1000ms ....");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }).start();
    */
    }

}


//@Inject    private CreateAndReadUserTask createAndReadUserTask;

//@Inject     private Instance<CreateAndReadUserTask> myTaskInstance;


//@Resource private javax.enterprise.concurrent.ManagedScheduledExecutorService managedScheduledExecutorService;
//@Resource private javax.enterprise.concurrent.ManagedExecutorService managedExecutorService;
    /*
    void createH2backup() {
        long period = 12;
        long initialDelay = period;
        TimeUnit timeUnit = TimeUnit.HOURS;
        managedScheduledExecutorService.scheduleAtFixedRate(() -> {

            try {
                LOGGER.info(">> H2 make backup ...");
                h2DatabaseBackup.makeBackupH2();
                LOGGER.info("<< H2 make backup ...");
            } catch (Exception e) {
                LOGGER.error("", e);
            }

        }, initialDelay, period, timeUnit);
    }

    void createUpdateExternalDnsRecordJob() {
        long period = 60;
        long initialDelay = 5;
        managedScheduledExecutorService.scheduleAtFixedRate(new UpdateExternalIpDnsRecord(), initialDelay, period, TimeUnit.SECONDS);
    }

     */
 /*
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        LOGGER.info("contextDestroyed()");
        //managedScheduledExecutorService.shutdownNow();
    }*/


//@Inject private Instance<GetTradesJob> getThradesJob;

    /*
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info(">> BackgroundJobs : contextInitialized()");
        //myTaskInstance.get();
        //managedScheduledExecutorService.scheduleAtFixedRate(new CreateAndReadUserTask(), 1, 5, TimeUnit.SECONDS);
        //managedScheduledExecutorService.scheduleAtFixedRate(createAndReadUserTask, initialDelay, period, TimeUnit.SECONDS);
        //createUpdateExternalDnsRecordJob();
        //createPullDataFromSensorsJob();
        //createH2backup();
        LOGGER.info("<< BackgroundJobs : contextInitialized()");
    }
    */
