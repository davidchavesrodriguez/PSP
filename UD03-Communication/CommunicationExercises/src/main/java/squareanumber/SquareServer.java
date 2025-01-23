package squareanumber;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;

public class SquareServer {
    public static void main(String[] args) throws IOException {
        try (var listener = new ServerSocket(59999)) {
            System.out.println("The server is running...");
            while (true) {
                try (var socket = listener.accept()) {
                    var out = new PrintWriter(socket.getOutputStream(), true);
                    out.println();
                }
            }
        }
    }
}
