package com.bp.RUIAN;

import com.bp.RUIAN.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private CsvService csvService;

    @Scheduled(cron = "${cron.value}", zone = "${cron.zone}")
    public void updateData() {
        try {
            csvService.updateData();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
