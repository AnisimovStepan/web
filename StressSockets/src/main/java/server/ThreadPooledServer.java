package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPooledServer implements Runnable{
    
    private int serverPort;
    private ServerSocket serverSocket;
    private ExecutorService threadPool = Executors.newFixedThreadPool(10);
    
    public ThreadPooledServer(int port){
        this.serverPort = port;
    }
    
    public void run(){
        openServerSocket();
        while (true) {
            Socket clientSocket;
            try {
                clientSocket = this.serverSocket.accept();
            } catch (IOException e) {
                throw new RuntimeException( "Error accepting client connection", e);
            }
            this.threadPool.execute(new WorkerRunnable(clientSocket));
        }
    }
    
    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            System.out.println("Server started");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port 8080", e);
        }
    }
}