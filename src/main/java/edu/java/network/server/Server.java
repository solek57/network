package edu.java.network.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(25237);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("socket started");
            handleRequest(client);
        }
    }

    private static void handleRequest(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
       // System.out.println("get word:" + br.readLine());
        StringBuilder sb = new StringBuilder("Hello ");
        sb.append(br.readLine());
        System.out.println("user: " + sb.toString());

        bw.write(sb.toString());
        bw.newLine();
        bw.flush();

        socket.close();
        br.close();
        bw.close();

    }
}
