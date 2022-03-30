package com.bp.ruian.scheduler;

import com.bp.ruian.config.AddressesImportScheduledTaskConfig;
import com.bp.ruian.record.Address;
import com.bp.ruian.services.EsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;


/**
 * {@link Address} import scheduler
 * @author denisprn
 */
@Component
@EnableScheduling
public class AddressesImportScheduledTask implements SchedulingConfigurer {
    private final AddressesImportScheduledTaskConfig scheduledTaskConfig;
    private final EsServiceImpl esServiceImpl;

    @Autowired
    public AddressesImportScheduledTask(AddressesImportScheduledTaskConfig scheduledTaskConfig,
                                        EsServiceImpl esServiceImpl) {
        this.scheduledTaskConfig = scheduledTaskConfig;
        this.esServiceImpl = esServiceImpl;
    }

    private void importData() {
        esServiceImpl.importAddresses();
    }

    @Override
    public void configureTasks(@NonNull ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addCronTask(new CronTask(this::importData, scheduledTaskConfig.getCronTrigger()));
    }
}
