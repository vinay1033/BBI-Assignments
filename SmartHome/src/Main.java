
import Classes.SmartHomeHub;
import Classes.SmartSpeaker;
import Classes.SmartTV;
import Classes.SmartThermostat;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SmartHomeHub hub = new SmartHomeHub(); // Create an instance of SmartHomeHub
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Smart Home System ---");
            System.out.println("1. Smart Thermostat");
            System.out.println("2. Smart Speaker");
            System.out.println("3. Smart TV");
            System.out.println("4. Connect All Devices to Network");
            System.out.println("5. Set Volume for Both TV and Speaker");
            System.out.println("6. Exit");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    controlThermostat(hub.getThermostat(), scanner);
                    break;
                case 2:
                    controlSpeaker(hub.getSpeaker(), scanner);
                    break;
                case 3:
                    controlTV(hub.getTV(), scanner);
                    break;
                case 4:
                    // Connect all devices to a network
                    System.out.print("Enter the Wi-Fi network name: ");
                    String network = scanner.next();
                    hub.connectToNetwork(network);
                    System.out.println("Thermostat:"+hub.getThermostat().getConnectedNetwork());
                    System.out.println("SmartSpeaker:"+hub.getSpeaker().getConnectedNetwork());
                    System.out.println("TV:"+hub.getTV().getConnectedNetwork());
                    break;

                case 5:
                    // Set the volume for both TV and Speaker
                    System.out.print("Enter the volume level for both TV and Speaker (0-100): ");
                    int volume = scanner.nextInt();

                    boolean tvOn = hub.getTV().isOn();
                    boolean speakerOn = hub.getSpeaker().isOn();

                    if (!tvOn && !speakerOn) {
                        System.out.println("Both TV and Speaker are OFF. Please turn them ON to set the volume.");
                    } else if (!tvOn) {
                        System.out.println("TV is OFF. Please turn it ON to set its volume.");
                        hub.getSpeaker().adjustVolume(volume);
                    } else if (!speakerOn) {
                        System.out.println("Speaker is OFF. Please turn it ON to set its volume.");
                        hub.getTV().adjustVolume(volume);
                    } else {
                        hub.getSpeaker().adjustVolume(volume);
                        hub.getTV().adjustVolume(volume);
                        System.out.println("Volume for both TV and Speaker set to " + volume);
                    }
                    break;

                case 6:
                    System.out.println("Exiting Smart Home System.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void controlThermostat(SmartThermostat thermostat, Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Smart Thermostat ---");
            System.out.println("1. Turn On");
            System.out.println("2. Turn Off");
            System.out.println("3. Connect to WiFi");
            System.out.println("4. Set Temperature");
            System.out.println("5. Get Temperature");
            System.out.println("6. Back");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    thermostat.turnOn();
                    break;
                case 2:
                    thermostat.turnOff();
                    break;
                case 3:
                    System.out.print("Enter WiFi network name: ");
                    String network = scanner.nextLine();
                    thermostat.connectToWiFi(network);
                    break;
                case 4:
                    System.out.print("Enter temperature to set: ");
                    int temp = scanner.nextInt();
                    thermostat.setTemperature(temp);
                    break;
                case 5:
                    int t = thermostat.getTemperature();
                    if (t == 0) {
                        System.out.println("Please set temperature first");
                    } else {
                        System.out.println("Temperature is: " + thermostat.getTemperature());
                    }
                    break;
                case 6:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void controlSpeaker(SmartSpeaker speaker, Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Smart Speaker ---");
            System.out.println("1. Turn On");
            System.out.println("2. Turn Off");
            System.out.println("3. Connect to WiFi");
            System.out.println("4. Get Network Name");
            System.out.println("5. Set Volume");
            System.out.println("6. Get Volume");
            System.out.println("7. Mute");
            System.out.println("8. Back");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    speaker.turnOn();
                    break;
                case 2:
                    speaker.turnOff();
                    break;
                case 3:
                    System.out.print("Enter WiFi network name: ");
                    String network = scanner.nextLine();
                    speaker.connectToWiFi(network);
                    break;

                case 4:
                    System.out.println("Network name is: " + speaker.getConnectedNetwork());
                    break;
                case 5:
                    System.out.print("Enter volume level: ");
                    int volume = scanner.nextInt();
                    speaker.adjustVolume(volume);
                    break;
                case 6:

                    if(speaker.getVolume()!=-1){
                        System.out.println("Volume is: " + speaker.getVolume());
                    }
                    break;
                case 7:
                    speaker.mute();
                    break;
                case 8:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void controlTV(SmartTV tv, Scanner scanner) {
        boolean back = false;
        while (!back) {
            System.out.println("\n--- Smart TV ---");
            System.out.println("1. Turn On");
            System.out.println("2. Turn Off");
            System.out.println("3. Connect to WiFi");
            System.out.println("4. Get Network");
            System.out.println("5. Set Volume");
            System.out.println("6. Mute");
            System.out.println("7. Back");
            System.out.print("Select an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    tv.turnOn();
                    break;
                case 2:
                    tv.turnOff();
                    break;
                case 3:
                    System.out.print("Enter WiFi network name: ");
                    String network = scanner.nextLine();
                    tv.connectToWiFi(network);

                    System.out.println(tv.getConnectedNetwork());
                    break;
                case 4:
                    System.out.println("Wifi name: "+tv.getConnectedNetwork());
                    break;
                case 5:
                    System.out.print("Enter volume level: ");
                    int volume = scanner.nextInt();
                    tv.adjustVolume(volume);
                    break;
                case 6:
                    tv.mute();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
