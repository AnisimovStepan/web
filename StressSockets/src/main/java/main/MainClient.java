package main;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class MainClient {
    private static final Logger log = LogManager.getLogger(MainClient.class.getName());
    
    public static void main(String[] args) throws IOException, InterruptedException {
        // new Thread(new Client()).start();
        // new Thread(new Client()).start();
    
        try {
            try {
                // адрес - локальный хост, порт - 4004, такой же как у сервера
                Socket clientSocket = new Socket("localhost", 5050); // этой строкой мы запрашиваем
                //  у сервера доступ на соединение
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                // читать соообщения с сервера
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                // писать туда же
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.println("Вы что-то хотели сказать? Введите это здесь:");
                // если соединение произошло и потоки успешно созданы - мы можем
                //  работать дальше и предложить клиенту что то ввести
                // если нет - вылетит исключение
                String word = reader.readLine(); // ждём пока клиент что-нибудь
                // не напишет в консоль
                out.write(word + "\n"); // отправляем сообщение на сервер
                out.flush();
                String serverWord;
                while ((serverWord = in.readLine()) != null) {
                    System.out.println(serverWord); // получив - выводим на экран
                }
            } finally { // в любом случае необходимо закрыть сокет и потоки
                System.out.println("Клиент был закрыт...");
                // clientSocket.close();
                // in.close();
                // out.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    
    }
    
    public static class Client implements Runnable{
    
        @Override
        public void run() {
            try {
                InetSocketAddress hostAddress = new InetSocketAddress("localhost", 5050);
    
                try (SocketChannel client = SocketChannel.open(hostAddress)){
                    log.info("Client started.");
        
                    // Send messages to server
                    String[] messages = new String[]{"Hello!", "Hello!", "Bye.", "Hello!"};
        
                    for (String msg : messages) {
                        byte[] message = msg.getBytes();
                        ByteBuffer buffer = ByteBuffer.wrap(message);
                        client.write(buffer);
                        log.info("Send to server: " + msg);
                        buffer.clear();
                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
