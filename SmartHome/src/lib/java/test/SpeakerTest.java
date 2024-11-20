package lib.java.test;

import Classes.SmartSpeaker;
import org.junit.Test;
import static org.junit.Assert.*;

public class SpeakerTest {

    @Test
    public void testPowerControl() {

        SmartSpeaker speaker = new SmartSpeaker();


        assertFalse("SmartSpeaker should be OFF initially.", speaker.isOn());


        speaker.turnOn();

        assertTrue("SmartSpeaker should be ON.", speaker.isOn());

    }

    @Test
    public void testWiFiConnection() {

        SmartSpeaker speaker = new SmartSpeaker();

        assertFalse("SmartSpeaker should not connect to WiFi when OFF.", speaker.checkConnectionStatus());

        speaker.turnOn();
        speaker.connectToWiFi("vinay");
        assertTrue("SmartSpeaker should connect to WiFi when ON.", speaker.checkConnectionStatus());

    }

    @Test
    public void testVolumeControl() {

        SmartSpeaker speaker = new SmartSpeaker();

        speaker.turnOn();

        speaker.connectToWiFi("vinay");

        assertEquals("Volume should be 0 when speaker is OFF.", 0, speaker.getVolume());


        speaker.adjustVolume(50);
        assertEquals("Volume should be set to 50.", 50, speaker.getVolume());


        speaker.adjustVolume(110);
        assertEquals("Volume should remain 50 after an invalid volume setting.", 110, speaker.getVolume());


        speaker.adjustVolume(-10);
        assertEquals("Volume should remain 110 after an invalid volume setting.", 110, speaker.getVolume());
    }

    @Test
    public void testMuteAndUnmute() {

        SmartSpeaker speaker = new SmartSpeaker();


        speaker.turnOn();

        speaker.connectToWiFi("vi-wifi");

        assertFalse("SmartSpeaker should not be muted when OFF.", speaker.isMuted());

        speaker.mute();
        assertTrue("SmartSpeaker should be muted when ON.", speaker.isMuted());


        speaker.unmute();
        assertFalse("SmartSpeaker should be unmuted.", speaker.isMuted());
    }

    @Test
    public void testPowerDependency() {

        SmartSpeaker speaker = new SmartSpeaker();

        assertFalse("SmartSpeaker should OFF initally .", speaker.checkConnectionStatus());


        assertFalse("SmartSpeaker should be connected to power before checking connected status .", speaker.checkConnectionStatus());

        speaker.turnOn();

        speaker.connectToWiFi("vi-wifi");

        assertTrue("SmartSpeaker should be muted when ON.", speaker.checkConnectionStatus());

    }

}


