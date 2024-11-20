package Classes;

import Interfaces.AudioControl;
import Interfaces.NetworkConnected;
import Interfaces.PowerControl;
import Interfaces.TemperatureControl;

//public class SmartTV implements PowerControl, NetworkConnected, TemperatureControl, AudioControl {
//    private boolean power;
//    private String connectedNetwork;
//    private int temperature;
//    private int volume;
//    private boolean muted;
//
//    public SmartTV() {
//        this.power = false;
//        this.connectedNetwork = null;
//        this.muted = false;
//    }
//
//    @Override
//    public void turnOn() {
//        this.power = true;
//        System.out.println("TV turned ON.");
//    }
//
//    @Override
//    public void turnOff() {
//        this.power = false;
//        disconnectFromWiFi();
//        System.out.println("TV turned OFF.");
//    }
//
//    @Override
//    public boolean isOn() {
//        return power;
//    }
//
//    @Override
//    public void connectToWiFi(String network) {
//        if (!power) {
//            System.out.println("Cannot connect TV to WiFi. Turn it ON first.");
//            return;
//        }
//        this.connectedNetwork = network;
//        System.out.println("TV connected to WiFi: " + network);
//    }
//
//    @Override
//    public void disconnectFromWiFi() {
//        this.connectedNetwork = null;
//        System.out.println("TV disconnected from WiFi.");
//    }
//
//    @Override
//    public boolean checkConnectionStatus() {
//        return connectedNetwork != null;
//    }
//
//    @Override
//    public String getConnectedNetwork() {
//        return connectedNetwork;
//    }
//
//    @Override
//    public void setTemperature(int temperature) {
//        this.temperature = temperature;
//    }
//
//    @Override
//    public int getTemperature() {
//        return temperature;
//    }
//
//    @Override
//    public void adjustVolume(int volume) {
//        this.volume = volume;
//        this.muted = false;
//    }
//
//    @Override
//    public void mute() {
//        this.muted = true;
//    }
//
//    @Override
//    public int getVolume() {
//        return volume;
//    }
//
//    @Override
//    public boolean isMuted() {
//        return muted;
//    }
//}
//


public class SmartTV implements PowerControl, NetworkConnected, AudioControl {
    private boolean power;
    private String connectedNetwork;
    private int volume;
    private boolean muted;

    public SmartTV() {
        this.power = false;
        this.connectedNetwork = null;
        this.muted = false;
    }

    @Override
    public void turnOn() {
        this.power = true;
        System.out.println("TV turned ON.");
    }

    @Override
    public void turnOff() {
        this.power = false;
        disconnectFromWiFi();
        System.out.println("TV turned OFF.");
    }

    @Override
    public boolean isOn() {
        return power;
    }

    @Override
    public void connectToWiFi(String network) {
        if (!power) {
            System.out.println("Cannot connect TV to WiFi. Turn it ON first.");
            return;
        }
        this.connectedNetwork = network;
        System.out.println("TV connected to WiFi: " + network);
    }

    @Override
    public void disconnectFromWiFi() {
        this.connectedNetwork = null;
        System.out.println("TV disconnected from WiFi.");
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
            System.out.println("Cannot set volume. Turn the TV ON first.");
            return;
        }

        if (connectedNetwork == null) {
            System.out.println("Cannot set volume. Please connect to WiFi first.");
            return;
        }


        if (volume < 0) {
            System.out.println("Invalid volume level! Setting volume to default: 30.");
            this.volume = 30;
        } else {
            this.volume = volume;
        }

        this.muted = false;
        System.out.println("TV volume set to " + this.volume);
    }

    @Override
    public int getVolume() {
        return volume;
    }

    @Override
    public void mute() {
        if (!power) {
            System.out.println("Cannot mute. Turn the TV ON first.");
            return;
        }

        if (connectedNetwork == null) {
            System.out.println("Cannot mute. Please connect the TV to WiFi first.");
            return;
        }

        this.muted = true;
        System.out.println("TV muted.");
    }


    @Override
    public void unmute() {
        if (!power) {
            System.out.println("Cannot unmute. Turn the TV ON first.");
            return;
        }

        if (connectedNetwork == null) {
            System.out.println("Cannot unmute. Please connect the TV to WiFi first.");
            return;
        }

        if (!muted) {
            System.out.println("TV is already unmuted.");
            return;
        }

        this.muted = false;
        System.out.println("TV unmuted. Current volume: " + volume);
    }

    @Override
    public boolean isMuted() {
        return muted;
    }
}

