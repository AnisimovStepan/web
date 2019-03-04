package main;

import server.NioServer;
import server.ThreadPooledServer;

public class Main {
    public static void main(String[] args) {
        NioServer server = new NioServer(5050);
        // ThreadPooledServer server = new ThreadPooledServer(5050);
        
        Thread serverThread = new Thread(server);
        
        serverThread.start();
        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
