package network;

import utils.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Network {
    public static void main(String[] args) throws InterruptedException, UnknownHostException {

        SocketClient client = new SocketClient("localhost", 1234);
        client.addConnectListener(socket -> {
            System.out.println("Connected");
            client.sendLine("Hello");
        });
        client.addMessageListener(line -> {
            System.out.println("Received: " + line);
        });
        client.addErrorListener(e -> e.printStackTrace());
        client.connect();
    }
}