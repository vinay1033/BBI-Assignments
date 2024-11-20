package Classes;

import Interfaces.AudioControl;
import Interfaces.NetworkConnected;
import Interfaces.PowerControl;

public class SmartSpeaker implements PowerControl, NetworkConnected, AudioControl {
    private boolean power;
    private String connectedNetwork;
    private int volume;
    private boolean muted;

    public SmartSpeaker() {
        this.power = false;
        this.connectedNetwork = null;
        this.muted = false;
    }

    @Override
    public void turnOn() {
        this.power = true;
        System.out.println("Speaker turned ON.");
    }

    @Override
    public void turnOff() {
        this.power = false;
        disconnectFromWiFi();
        System.out.println("Speaker turned OFF.");
    }

    @Override
    public boolean isOn() {
        return power;
    }

    @Override
    public void connectToWiFi(String network) {
        if (!power) {
            System.out.println("Cannot connect Speaker to WiFi. Turn it ON first.");
            return;
        }
        this.connectedNetwork = network;
        System.out.println("Speaker connected to WiFi: " + network);
    }

    @Override
    public void disconnectFromWiFi() {
        this.connectedNetwork = null;
        System.out.println("Speaker disconnected from WiFi.");
    }

    @Override
    public boolean checkConnectionStatus() {
        return connectedNetwork != null;
    }

    @Override
    public String getConnectedNetwork() {
        if (!power) {
            return "Device is OFF. Not connected to any network.";
        }
        if (connectedNetwork == null) {
            return "Device is not connected to any network.";
        }
        return connectedNetwork;
    }



    @Override
    public void adjustVolume(int volume) {
        if (!power) {
            System.out.println("Cannot set volume. Turn the SmartSpeaker ON first.");
            return;
        }

        if (connectedNetwork == null) {
            System.out.println("Cannot set volume. Please connect to WiFi first.");
            return;
        }

        if (volume < 0) {
            System.out.println("Invalid volume. Volume cannot be negative.");
            return;
        }

        this.volume = volume;
        this.muted = false;
        System.out.println("Volume set to " + volume);
    }


    @Override
    public int getVolume() {
        if (!power) {
            System.out.println("Cannot get volume. The SmartSpeaker is OFF.");
            return -1;
        }

         else if (connectedNetwork == null) {
            System.out.println("Cannot get volume. The SmartSpeaker is not connected to WiFi.");
            return -1;
        }

        return volume;
    }

    @Override
public void mute() {
    if (!power) {
        System.out.println("Cannot mute. Turn the Speaker ON first.");
        return;
    }

    if (connectedNetwork == null) {
        System.out.println("Cannot mute. Please connect the Speaker to WiFi first.");
        return;
    }

    this.muted = true;
    System.out.println("Speaker muted.");
}


    @Override
    public void unmute() {
        if (!power) {
            System.out.println("Cannot unmute. Turn the Speaker ON first.");
            return;
        }

        if (connectedNetwork == null) {
            System.out.println("Cannot unmute. Please connect the Speaker to WiFi first.");
            return;
        }

        if (!muted) {
            System.out.println("Speaker is already unmuted.");
            return;
        }

        this.muted = false;
        System.out.println("Speaker unmuted. Current volume: " + volume);
    }


    @Override
    public boolean isMuted() {
        return muted;
    }
}
