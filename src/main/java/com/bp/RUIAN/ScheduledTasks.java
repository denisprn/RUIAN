package com.bp.RUIAN;

import com.bp.RUIAN.services.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    private CsvService csvService;

    @Scheduled(cron = "0 0 1 1 * ?", zone = "Europe/Prague") //At 01:00:00am, on the 1st day, every month
    public void updateData() {
        try {
            csvService.updateData();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
