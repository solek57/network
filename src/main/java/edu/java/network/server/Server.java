package edu.java.network.server;

import edu.java.network.Greetadle;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(25238);
        while (true) {
            Socket client = serverSocket.accept();
            System.out.println("socket started");
            new Thread(() -> {
                try {
                    //Thread.sleep(2000);
                    handleRequest(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

    private static void handleRequest(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        // System.out.println("get word:" + br.readLine());
        String[] requestLines = br.readLine().split("\\s+");
        String command = requestLines[0];
        String userName = requestLines[1];

        System.out.println(requestLines[0].toString());

        String response = buildResponse(command, userName);


        bw.write(response);
        bw.newLine();
        bw.flush();

        socket.close();
        br.close();
        bw.close();

    }

    private static String buildResponse(String command, String userName) {
        Map<String, Greetadle> map = handlers();
        System.out.println("command"+command);
        Greetadle greetadle = map.get(command);
        if (greetadle != null)
            return greetadle.buildResponse(userName);
        else
            return "hello" + userName;
    }

    private static Map<String, Greetadle> handlers() {
        Map<String, Greetadle> map = new HashMap<>();

        try (InputStream is = Server.class.getClassLoader().getResourceAsStream("server.properties")) {

            Properties properties = new Properties();
            properties.load(is);

            for (Object command : properties.keySet()) {
                String className = properties.getProperty(command.toString());
                Class<Greetadle> greetadleClass = (Class<Greetadle>) Class.forName(className);
                Greetadle greetadle = greetadleClass.getConstructor().newInstance();
                map.put(command.toString(), greetadle);
            }

        } catch (IOException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
}
