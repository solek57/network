package edu.java.network.client;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws IOException {
        sendRequest();
    }

    private static void sendRequest() throws IOException {
        Socket socket = new Socket("127.0.0.1", 25237);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String sb = "Dmitry ";

        bw.write(sb);
        bw.newLine();
        bw.flush();

        String result = br.readLine();
        System.out.println(result);

        System.out.println("result: " + result);

        socket.close();
        br.close();
        bw.close();

    }
}
