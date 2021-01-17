package edu.java.network.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(25238);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("socket started");
            new Thread(() -> {
                try {
                    Thread.sleep(2000);
                    handleRequest(client);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    private static void handleRequest(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // System.out.println("get word:" + br.readLine());
        StringBuilder sb = new StringBuilder("Hello ");
        String name = br.readLine();

        sb.append(name);
        if ("Dmitry 1".equals(name))
            sb.append("это тот кто нужен");
        System.out.println("user: " + sb.toString());

        bw.write(sb.toString());
        bw.newLine();
        bw.flush();

        socket.close();
        br.close();
        bw.close();

    }
}
