package com.projects.irrigationcontrollerbatch.writer;

import com.projects.irrigationcontrollerbatch.helper.IrrigationSystemConstants;
import com.projects.irrigationcontrollerbatch.model.IrrigationSystem;
import com.projects.irrigationcontrollerbatch.model.Zone;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Calendar;
import java.util.List;

/**
 * @author Ryan G. Marcoux
 */
@Component
public class IrrigationSystemWriter  implements ItemWriter<IrrigationSystem> {

    @Autowired
    RestTemplate restTemplate;

    final String ROOT_URI = "http://192.168.1.111:9999/system/zone";

    @Override
    public void write(List<? extends IrrigationSystem> list) {

        Calendar cal = Calendar.getInstance();

        for (IrrigationSystem system: list) {

            if (system.isActive()) {

                if (isTimeToRun(system, cal) && isDayToRun(system, cal)) {

                    for (Zone zone : system.getZones()) {

                        if (zone.isActiveIndicator()) {
                            restTemplate.exchange(determineZoneURL(zone), HttpMethod.GET, null, String.class);
                        }
                    }
                }
            }
        }
    }

    /**
     * Determines if it is the correct time to start system based on current time and start time
     * @param irrigationSystem
     * @return
     */
    public boolean isTimeToRun(IrrigationSystem irrigationSystem, Calendar cal) {

        String startTime = null;
        String currentTime = cal.get(Calendar.HOUR_OF_DAY)+ ":" + cal.get(Calendar.MINUTE);

        if (null != irrigationSystem) {
            startTime = irrigationSystem.getStartTime();
        }

        return startTime.equals(currentTime);
    }

    /**
     * Determines if today is a day to run based off of frequency and current date
     * @param system
     * @return
     */
    public boolean isDayToRun(IrrigationSystem system, Calendar calendar) {

        boolean isDayToRun = false;

        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        if (null != system) {

            String frequency = system.getFrequency();
            if (null != frequency) {

                if (IrrigationSystemConstants.Frequency.ALL.equals(frequency)) {
                    isDayToRun = true;
                }
                else if (IrrigationSystemConstants.Frequency.EVEN.equals(frequency)) {

                    if (dayOfMonth % 2 == 0) {
                        isDayToRun = true;
                    }
                }
                else if (IrrigationSystemConstants.Frequency.ODD.equals(frequency)) {
                    if (dayOfMonth % 2 != 0) {
                        isDayToRun = true;
                    }
                }
            }
        }

        return isDayToRun;
    }

    public String determineZoneURL(Zone zone) {
        return  ROOT_URI + "/" + zone.getZoneNumber() + "/" + zone.getRunTime();
    }
}
