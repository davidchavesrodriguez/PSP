package randomword;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RandomWordServerWorker implements Runnable{
    private final DatagramSocket socketUDP;
    private final DatagramPacket request;

    public RandomWordServerWorker(DatagramSocket socketUDP, DatagramPacket request) {
        this.socketUDP = socketUDP;
        this.request = request;
    }


    @Override
    public void run() {
        try {
            // Get client data
            String command = new String(request.getData(), 0, request.getLength()).trim();
            System.out.println("Received: " + command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
