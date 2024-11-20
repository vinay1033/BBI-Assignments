package Interfaces;

public interface NetworkConnected {
    void connectToWiFi(String network);
    void disconnectFromWiFi();
    boolean checkConnectionStatus();
    String getConnectedNetwork();
}


