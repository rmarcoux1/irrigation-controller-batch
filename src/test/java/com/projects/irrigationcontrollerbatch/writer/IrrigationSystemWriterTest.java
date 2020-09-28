package com.projects.irrigationcontrollerbatch.writer;

import com.projects.irrigationcontrollerbatch.model.IrrigationSystem;
import com.projects.irrigationcontrollerbatch.model.Zone;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Ryan G. Marcoux
 */
class IrrigationSystemWriterTest {

    private IrrigationSystemWriter writer;

    @BeforeEach
    public void setup() {
        writer = new IrrigationSystemWriter();
    }

    @Test
    public void test_isTimeToRun_pos() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,27);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "All",
                "12:35",1,20);

        Assert.assertTrue(writer.isTimeToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isTimeToRun_neg() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,27);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 00);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "All",
                "12:35",1,20);

        Assert.assertFalse(writer.isTimeToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isDayToRun_pos_all_1() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,27);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "All",
                "12:35",1,20);

        Assert.assertTrue(writer.isDayToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isDayToRun_pos_all_2() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,26);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "All",
                "12:35",1,20);

        Assert.assertTrue(writer.isDayToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isDayToRun_pos_odd() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,27);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "Odd",
                "12:35",1,20);

        Assert.assertTrue(writer.isDayToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isDayToRun_pos_even() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,26);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "Even",
                "12:35",1,20);

        Assert.assertTrue(writer.isDayToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isDayToRun_neg_even() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,27);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "Even",
                "12:35",1,20);

        Assert.assertFalse(writer.isDayToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_isDayToRun_neg_odd() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH,26);
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 35);

        IrrigationSystem irrigationSystem = createIrrigationSystem(true, "Odd",
                "12:35",1,20);

        Assert.assertFalse(writer.isDayToRun(irrigationSystem,calendar));

    }

    @Test
    public void test_determineZoneURL_pos_1() {
        Zone zone = new Zone();
        zone.setZoneNumber(1);
        zone.setRunTime(30);

        String url = writer.determineZoneURL(zone);

        Assert.assertEquals(url,"http://192.168.1.111:9999/system/zone/1/30");

    }

    @Test
    public void test_determineZoneURL_pos_2() {
        Zone zone = new Zone();
        zone.setZoneNumber(6);
        zone.setRunTime(45);

        String url = writer.determineZoneURL(zone);

        Assert.assertEquals(url,"http://192.168.1.111:9999/system/zone/6/45");

    }

    private IrrigationSystem createIrrigationSystem (boolean activeIndicator,
                                                     String frequency, String startTime,
                                                     int zoneNumber, int runtTime) {

        IrrigationSystem irrigationSystem = new IrrigationSystem();
        irrigationSystem.setStartTime(startTime);
        irrigationSystem.setFrequency(frequency);
        irrigationSystem.setActive(activeIndicator);

        List<Zone> zones = new ArrayList<>();
        zones.add(createZone(zoneNumber, runtTime));

        irrigationSystem.setZones(zones);

        return irrigationSystem;
    }

    private Zone createZone(int zoneNumber, int runTime) {

        Zone zone = new Zone();
        zone.setZoneNumber(zoneNumber);
        zone.setRunTime(runTime);

        return zone;
    }
}