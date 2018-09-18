package com.ilirium.basicee;

import org.slf4j.*;

import javax.enterprise.inject.*;
import javax.enterprise.inject.spi.*;

public class ProducersFactory {

    @Produces
    public Logger createLogger(final InjectionPoint ip) {
        return LoggerFactory.getLogger(ip.getMember().getDeclaringClass());
    }

}
