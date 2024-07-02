package network;

import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.SocketAddress;
import java.util.Enumeration;

public class reconnect{
    private static final String SERVER_ADDRESS = "10.26.10.217"; // 服务器地址
    private static final int PORT = 8080; // 服务器端口
    private static final String SAVE_FILE = "data/connection_info.txt"; // 保存连接信息的文件

    public static void main(String[] args) {
        try {
            // 保存当前连接信息
            //saveConnectionInfo();

            // 尝试连接服务器
            connectServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*private static void saveConnectionInfo() throws Exception {
        // 获取网络接口，检查网络状态
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            if (networkInterface.isUp()) {
                //SocketAddress socketAddress = networkInterface.getInetAddresses().nextElement();
                if (socketAddress instanceof InetSocketAddress) {
                    InetSocketAddress inetSocketAddress = (InetSocketAddress) socketAddress;
                    String ipAddress = inetSocketAddress.getAddress().getHostAddress();
                    System.out.println("IP Address: " + ipAddress);
                    break;
                }
            }
        }

        // 保存IP地址到文件，用于重连时使用
        // TODO: 实现保存到文件的逻辑
    }*/

    private static void connectServer() throws Exception {
        // TODO: 实现连接服务器的逻辑，包括读取保存的连接信息，尝试重新连接等。
    }
}