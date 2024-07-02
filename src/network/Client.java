package network;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends SocketBase{
    private Socket socket;
    private OutputStream outputStream;

    public Client() {
        try {
            // 创建Socket连接
            socket = new Socket("10.26.10.217",1234);
            handleConnection(socket);
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    public Socket getSocket() {return socket;}

}