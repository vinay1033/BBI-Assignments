package Classes;

import Classes.SmartSpeaker;
import Classes.SmartTV;
import Classes.SmartThermostat;

public class SmartHomeHub {
    private SmartThermostat thermostat = new SmartThermostat();
    private SmartSpeaker speaker = new SmartSpeaker();
    private SmartTV tv = new SmartTV();
    private String connectedNetwork;

    public SmartThermostat getThermostat() {
        return thermostat;
    }

    public SmartSpeaker getSpeaker() {
        return speaker;
    }

    public SmartTV getTV() {
        return tv;
    }

    public void connectToNetwork(String network) {
        if (network == null || network.isEmpty()) {
            System.out.println("Invalid network.");
            return;
        }

        this.connectedNetwork = network;

        // Propagate the network name to the devices only if they are ON
        if (thermostat.isOn()) {
            thermostat.connectToWiFi(network);
        } else {
            thermostat.disconnectFromWiFi();
        }

        if (speaker.isOn()) {
            speaker.connectToWiFi(network);
        } else {
            speaker.disconnectFromWiFi();
        }

        if (tv.isOn()) {
            tv.connectToWiFi(network);
        } else {
            tv.disconnectFromWiFi();
        }

        System.out.println("Devices connected to network: " + network);
    }

    public String getConnectedNetwork() {
        return connectedNetwork;
    }
}
