package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.function.Consumer;

public abstract class SocketBase {
    protected Consumer<Socket> connectCallback = socket -> {
    };//连接返回
    protected Consumer<String> receiveCallback = line -> {
    };//接收后返回

    protected Consumer<IOException> errorCallback = e -> {
    };//错误返回
    Socket conn;

    //连接成功建立时，这个Listener被触发
    public void addConnectListener(Consumer<Socket> listener) {
        connectCallback = listener;
    }

    //收到一行字符串时，此Listener被触发
    public void addMessageListener(Consumer<String> listener) {
        receiveCallback = listener;
    }
    //各种报错时触发。这个Listener被触发时连接会断开
    public void addErrorListener(Consumer<IOException> listener) {
        errorCallback = listener;
    }

    protected void handleConnection(Socket socket) throws IOException {
        conn = socket;
        connectCallback.accept(conn);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String msg;
        while ((msg = in.readLine()) != null) {
            receiveCallback.accept(msg);
        }
        handleError(new IOException("Remote disconnected"));
    }

    protected void handleError(IOException e) {
        conn = null;
        errorCallback.accept(e);
    }
    //用于发送一行字符串。返回值为true代表这行字符串成功发送。
    public boolean sendLine(String line) {
        if (conn == null) {
            errorCallback.accept(new IOException("Not connected"));
            return false;
        }
        try {
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
            out.println(line);
            return true;
        } catch (IOException e) {
            handleError(e);
        }
        return false;
    }
}
