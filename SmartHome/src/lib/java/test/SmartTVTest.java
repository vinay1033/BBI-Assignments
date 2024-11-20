package lib.java.test;
import Classes.SmartTV;
import org.junit.Test;
import static org.junit.Assert.*;
public class SmartTVTest {
    @Test
    public void testPowerControl() {

        SmartTV tv = new SmartTV();


        assertFalse("SmartTV should be OFF initially.", tv.isOn());


        tv.turnOn();
        assertTrue("SmartTV should be ON.", tv.isOn());


        tv.turnOff();
        assertFalse("SmartTV should be OFF.", tv.isOn());
    }

    @Test
    public void testVolumeControl() {

        SmartTV tv = new SmartTV();


        tv.adjustVolume(50);
        assertEquals("Volume should not change when the TV is OFF.", 0, tv.getVolume());


        tv.turnOn();
        tv.adjustVolume(50);
        assertEquals("Volume should not change without WiFi connection.", 0, tv.getVolume());


        tv.connectToWiFi("HomeWiFi");
        tv.adjustVolume(-10);
        assertEquals("Volume should be set to 50.", 30, tv.getVolume());


        tv.adjustVolume(120);
        assertEquals("Volume should remain 50 after setting an invalid value.", 120,tv.getVolume());
    }


    @Test
    public void testMuteAndUnmute() {

        SmartTV tv = new SmartTV();

        tv.adjustVolume(35);
        tv.getVolume();

        tv.mute();
        assertFalse("SmartTV should not be muted when OFF.", tv.isMuted());


        tv.turnOn();
        tv.connectToWiFi("HomeWiFi");


        tv.mute();
        assertTrue("SmartTV should be muted.", tv.isMuted());


        tv.unmute();
        assertFalse("SmartTV should be unmuted.", tv.isMuted());
    }



}
