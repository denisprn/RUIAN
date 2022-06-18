package com.bp.ruian.config;

import com.bp.ruian.record.Address;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * {@link Address} import scheduler configuration
 * @author denisprn
 */
@Configuration
@ConfigurationProperties(prefix = AddressesImportScheduledTaskConfig.CONFIG_PROP_PREFIX)
public class AddressesImportScheduledTaskConfig {

    public static final String CONFIG_PROP_PREFIX = "addresses-import.scheduled-task.cron";

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
}
