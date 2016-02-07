package com.kineo.job;

import com.kineo.util.JobsCounter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Mariano on 1/31/2016.
 */
abstract class BaseJob {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseJob.class);

    public void doWork() {
        try {
            LOGGER.trace("Starting job {}", this.getClass().getName());
            JobsCounter.add();
            work();
        } catch (Exception e) {
            LOGGER.error("Fail trying doJob", e);
        } finally {
            LOGGER.trace("Ended job {}", this.getClass().getName());
            JobsCounter.subtract();
        }
    }

    abstract void work();
}
