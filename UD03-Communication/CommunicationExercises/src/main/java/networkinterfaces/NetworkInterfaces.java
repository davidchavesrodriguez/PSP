package networkinterfaces;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

public class NetworkInterfaces {
    public static void main(String[] args) {
        try {
            // Get all network interfaces available on the system
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

            // Loop through each network interface
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // Check if the network interface is up (active)
                if (networkInterface.isUp()) {
                    // Print the name of the network interface
                    System.out.println("Interface name: " + networkInterface.getName());

                    // Get all IP addresses associated with the network interface
                    List<InterfaceAddress> addresses = networkInterface.getInterfaceAddresses();
                    boolean hasAddress = false;

                    // Loop through the addresses and check their types (IPv4 or IPv6)
                    for (InterfaceAddress address : addresses) {
                        InetAddress inetAddress = address.getAddress();

                        // Check if the address is IPv4
                        if (inetAddress instanceof Inet4Address) {
                            System.out.println("IPv4 Address: " + inetAddress.getHostAddress());
                            hasAddress = true;
                        }
                        // Check if the address is IPv6
                        else if (inetAddress instanceof Inet6Address) {
                            System.out.println("IPv6 Address: " + inetAddress.getHostAddress());
                            hasAddress = true;
                        }
                    }

                    // If no IP address was found for the interface
                    if (!hasAddress) {
                        System.out.println("\tNo IP address assigned.");
                    }

                    // Print a separator for better readability
                    System.out.println("---------------------------------");
                }
            }

        } catch (SocketException e) {
            // Handle any exceptions that occur while retrieving network interfaces
            System.err.println("Error retrieving network interfaces: " + e.getMessage());
        }
    }
}
