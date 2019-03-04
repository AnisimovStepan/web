package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class WorkerRunnable implements Runnable{
    
    private Socket clientSocket;
    
    public WorkerRunnable(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }
    
    public void run() {
        try {
            try (BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
                try (OutputStream output = clientSocket.getOutputStream()) {
                
                    String line;
                    while ((line = input.readLine()) != null) {
                        System.out.println("Get message: " + line);
                        if (line.equals("Bye.")) {
                            clientSocket.close();
                            return;
                        }
                        line = line + '\n';
                        output.write(line.getBytes());
                    }
                }
            }
            // System.out.println("Request processed: " + time);
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }
}
