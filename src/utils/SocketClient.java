package utils;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class SocketClient extends SocketBase {
    String address;
    int port;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void connect() {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        exec.submit(() -> {
            try {
                handleConnection(new Socket(address, port));
            } catch (IOException e) {
                handleError(e);
            }
            exec.shutdown();
        });
    }
}
