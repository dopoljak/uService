package com.ilirium.webservice.background;

//import org.jboss.weld.context.RequestContext;
//import org.jboss.weld.context.unbound.UnboundLiteral;

import javax.enterprise.inject.spi.CDI;

/**
 *  !!!! work-in-progress - example !!!!
 *  TODO: find non weld dependant way to attach RequestContext/ApplicationContext to new thread !
 */
/**
 * Created by DoDo on 15.7.2017..
 */
public abstract class BaseJob implements Runnable {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(BaseJob.class);

    @Override
    public void run() {
        /*
        RequestContext requestContext = CDI.current().select(RequestContext.class, UnboundLiteral.INSTANCE).get();
        try {
            LOGGER.info(">> Activate request scoped");
            requestContext.activate();
            execute();
        } finally {
            LOGGER.info("<< Deactivate request scoped");
            requestContext.deactivate();
        }*/
    }

    public abstract void execute();
}
