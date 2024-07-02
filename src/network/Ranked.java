package network;

import java.net.*;
import java.io.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Ranked extends SocketBase{
    private Socket socket;
    private OutputStream outputStream;
    String[] args =new String[5];
    final String filename="data/ranking.txt/";
    private void cout(String data) throws IOException {
        File outfile = new File(filename);
        PrintWriter out = new PrintWriter(outfile, "UTF-8");
        out.println(data);
        System.out.println("The data has been saved!");
        // 释放资源
        out.close();
    }
    public String cin() throws IOException {
        File infile = new File(filename);
        Scanner in = new Scanner(infile, "UTF-8");
        String data=new String(in.nextLine());
        for(int i=1;i<5;i++) {
            String str= in.nextLine();
            data+=(" "+str);
        }
        in.close();
        return data;
        // 释放资源
    }

    public Ranked() {
        try {
            // 创建Socket连接
            socket = new Socket("10.26.10.217",1234);
            handleConnection(socket);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String  line = "apply for ranking";
            out.println(line);
            out.flush();
            String data = in.readLine();
            System.out.println(data);
            cout(data);
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
    public Socket getSocket() {return socket;}
    public String getName(int id){
        return args[id];
    }
}