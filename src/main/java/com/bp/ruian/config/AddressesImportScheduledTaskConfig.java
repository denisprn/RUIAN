package com.bp.ruian.config;

import com.bp.ruian.record.Address;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.support.CronTrigger;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


/**
 * {@link Address} import scheduler configuration
 * @author denisprn
 */
@Configuration
@ConfigurationProperties(prefix = "addresses-import.scheduled-task.cron")
public class AddressesImportScheduledTaskConfig {

    private String value;

    private String timeZone;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    private CronTrigger cronTrigger;

    @PostConstruct
    public void init() {
        cronTrigger = new CronTrigger(value, TimeZone.getTimeZone(timeZone));
    }

    public CronTrigger getCronTrigger() {
        return cronTrigger;
    }
}
