package com.ilirium.webservice.background;

import com.ilirium.basic.*;
import com.ilirium.webservice.generator.*;
import org.slf4j.*;

import javax.annotation.*;
import javax.ejb.*;
import javax.inject.*;

public abstract class BackgroundJob {

    @Inject
    private Logger LOGGER;
    @Inject
    private CorrelationIdGenerator correlationIdGenerator;

    @Resource
    private TimerService timerService;

    protected Logger getLogger() {
        return LOGGER;
    }

    @PostConstruct
    public void init() {
        setUpTimerService();
    }

    public void setUpTimerService() {
        try {
            getLogger().info("Setting up timer service!");

            ScheduleExpression schedule = new ScheduleExpression();
            if (getDayOfWeek() != null) {
                schedule.dayOfWeek(getDayOfWeek());
            }
            if (getHour() != null) {
                schedule.hour(getHour());
            }
            if (getMinute() != null) {
                schedule.minute(getMinute());
            }

            TimerConfig timerConfig = new TimerConfig();
            timerConfig.setPersistent(false);
            timerService.createCalendarTimer(schedule, timerConfig);
            getLogger().info("Timer service set for: {}!", schedule);
        } catch (Exception e) {
            getLogger().error("Error occurred during setting up timer service!", e);
        }
    }

    @Timeout
    public void execute(Timer t) {
        setCorrelationId();
        getLogger().info("{} background job started!", getJobName());
        try {
            runJob();
        } catch (Exception e) {
            getLogger().error("Error executing background job!", e);
            throw new RuntimeException(e);
        }
        getLogger().info("{} background job ended!", getJobName());
    }

    protected void setCorrelationId() {
        MDC.put(Const.CORRELATION_ID, getJobName() + "-" + correlationIdGenerator.getNextId(0));
    }

    protected abstract void runJob();

    protected abstract String getJobName();

    protected String getDayOfWeek() {
        return null;
    }

    protected String getHour() {
        return null;
    }

    protected String getMinute() {
        return null;
    }

}
