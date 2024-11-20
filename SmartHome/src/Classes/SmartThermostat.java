package Classes;

import Interfaces.NetworkConnected;
import Interfaces.PowerControl;
import Interfaces.TemperatureControl;

public class SmartThermostat implements PowerControl, NetworkConnected, TemperatureControl {
    private boolean power;
    private String connectedNetwork;
    private int temperature;

    public SmartThermostat() {
        this.power = false;
        this.connectedNetwork = null;

    }

    @Override
    public void turnOn() {
        this.power = true;
        System.out.println("Thermostat turned ON.");
    }

    @Override
    public void turnOff() {
        this.power = false;
        disconnectFromWiFi();
        System.out.println("Thermostat turned OFF.");
    }

    @Override
    public boolean isOn() {
        return power;
    }

    @Override
    public void connectToWiFi(String network) {
        if (!power) {
            System.out.println("Cannot connect Thermostat to WiFi. Turn it ON first.");
            return;
        }
        this.connectedNetwork = network;
        System.out.println("Thermostat connected to WiFi: " + network);
    }

    @Override
    public void disconnectFromWiFi() {
        this.connectedNetwork = null;
        System.out.println("Thermostat disconnected from WiFi.");
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

    public void setTemperature(int temperature) {
        if (!power) {
            System.out.println("Cannot set temperature. Turn the Thermostat ON first.");
            return;
        }

        if (connectedNetwork == null) {
            System.out.println("Cannot set temperature. Please connect to WiFi first.");
            return;
        }


        if (temperature < 25 || temperature > 10) {
            System.out.println("Temperature out of range! Setting temperature to default value: 21°C.");
            this.temperature = 21;
        } else {
            this.temperature = temperature;
        }

        System.out.println("Temperature set to " + this.temperature + "°C.");
    }

    @Override
    public int getTemperature() {
        return temperature;
    }
}

