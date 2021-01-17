package edu.java.network.client;

import java.io.*;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Date;

public class Client {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                try {
                    System.out.println("started " + finalI + ": " + LocalDateTime.now());
                    Thread.sleep(2000);
                    String re = sendRequest(finalI);
                    System.out.println("finish " + finalI + "@" + re + "@" + ": " + LocalDateTime.now());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }

    }

    private static String sendRequest(Integer i) throws IOException {
        Socket socket = new Socket("127.0.0.1", 25238);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        String sb = "Dmitry " + i;

        bw.write(sb);
        bw.newLine();
        bw.flush();

        String result = br.readLine();
        System.out.println(result);

        socket.close();
        br.close();
        bw.close();

        return result;

    }
}
