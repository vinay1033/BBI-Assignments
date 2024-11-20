package lib.java.test;


import Classes.SmartSpeaker;
import Classes.SmartThermostat;
import org.junit.Test;
import static org.junit.Assert.*;
public class ThermostatTest {

    @Test
    public void testPowerControl() {

        SmartThermostat thermostat = new SmartThermostat();


        assertFalse("thermostat should be OFF initially.", thermostat.isOn());


        thermostat.turnOn();

        assertTrue("thermostat should be ON.", thermostat.isOn());

    }

    @Test
    public void testWiFiConnection() {

        SmartThermostat thermostat = new SmartThermostat();

        assertFalse("thermostat should not connect to WiFi when OFF.", thermostat.checkConnectionStatus());

        thermostat.turnOn();
        thermostat.connectToWiFi("vinay");
        assertTrue("thermostat should connect to WiFi when ON.", thermostat.checkConnectionStatus());

    }

    @Test
    public void testPowerDependency() {

        SmartThermostat thermostat = new SmartThermostat();

        assertFalse("thermostat should OFF initally .", thermostat.checkConnectionStatus());


        assertFalse("thermostat should be connected to power before checking connected status .", thermostat.checkConnectionStatus());

        thermostat.turnOn();

        thermostat.connectToWiFi("vi-wifi");

        assertTrue("thermostat should function after connecting wifi.", thermostat.checkConnectionStatus());
        thermostat.setTemperature(30);
        thermostat.getTemperature();

    }


    @Test
    public void testVolumeControl() {

        SmartThermostat thermostat = new SmartThermostat();

        thermostat.turnOn();

        thermostat.connectToWiFi("vinay");

        assertEquals("temperature should be 0 when thermostat is OFF.", 0, thermostat.getTemperature());


//        thermostat.setTemperature(24);
//        assertEquals("temperature should be set to 20.", 21, thermostat.getTemperature());


        thermostat.setTemperature(120);
        assertEquals("temperature should remain 50 after an invalid volume setting.", 21, thermostat.getTemperature());


        thermostat.setTemperature(-10);
        assertEquals("temperature can be also set to -10", 21, thermostat.getTemperature());
    }

}
