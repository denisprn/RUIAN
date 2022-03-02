package com.bp.RUIAN;

import com.bp.RUIAN.services.CsvService;
import com.bp.RUIAN.services.EsService;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@Component
@Profile("production")
public class ScheduledTasks {
    @Autowired
    private EsService esService;

    @Scheduled(cron = "${cron.value}", zone = "${cron.zone}")
    public void updateData() throws FileNotFoundException, UnsupportedEncodingException {
        esService.updateData();
    }
}
