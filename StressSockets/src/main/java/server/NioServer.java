package server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NioServer implements Runnable {
    private static final Logger log = LogManager.getLogger(NioServer.class.getName());
    
    private Selector serverSelector;
    private InetSocketAddress listenAddress;
    private static final String STOP_WORD = "Bye.";
    
    private String message;
    
    public NioServer(int port) {
        this.listenAddress = new InetSocketAddress("localhost", port);
    }
    
    @Override
    public void run() {
        try {
            // Open sever channel and its selector
            serverSelector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            
            // Retrieve server socket and bind to port
            serverChannel.socket().bind(listenAddress);
            serverChannel.register(serverSelector, SelectionKey.OP_ACCEPT);
            
            // log.info("Server started.");
            System.out.println("Server started.");
            // log.info("Server started.");
            
            while (true) {
                // Wait for events
                serverSelector.select();
                
                // Work on selected keys
                Iterator keys = serverSelector.selectedKeys().iterator();
                while (keys.hasNext()) {
                    SelectionKey key = (SelectionKey) keys.next();
                    
                    // This is necessary to prevent the same key from coming up
                    // again the next time around.
                    keys.remove();
                    
                    if (!key.isValid()) {
                        continue;
                    }
                    
                    if (key.isAcceptable()) {
                        // Go to accept
                        this.accept(key);
                    } else if (key.isReadable()) {
                        // Go to read
                        this.read(key);
                    }
                    // else if (key.isWritable()) {
                    //     // Go to write
                    //     this.write(key);
                    // }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    // Accept a connection made to this channel's socket
    private void accept(SelectionKey key) throws IOException {
        SocketChannel channel = ((ServerSocketChannel) key.channel()).accept();
        // Set config that mean we did'nt block this channel
        channel.configureBlocking(false);
        Socket socket = channel.socket();
        SocketAddress remoteAddr = socket.getRemoteSocketAddress();
        // log.info("Connected to: " + remoteAddr);
        
        // Register channel with selector for further IO
        channel.register(serverSelector, SelectionKey.OP_READ);
    }
    
    // Read from the socket channel
    private void read(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(256);
            int numRead = channel.read(buffer);
    
            // If channel is closed
            if (numRead == -1) {
                closeConnection(key, false);
                return;
            }
    
            // If ok read message
            message = new String(buffer.array()).trim();
            // log.info("Got: " + message);
            // If we get message to close connection, so close client
            if (message.equals(STOP_WORD)) {
                closeConnection(key, true);
                return;
            }
            
            // Echo
            buffer.flip();
            channel.write(buffer);
            buffer.clear();
            
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            finally {
                key.cancel();
            }
        }
    }
    
    // Read from the socket channel
    private void write(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            byte[] msgBuffer = (message + '\n').getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(msgBuffer);
            channel.write(buffer);
            // log.info(String.format("Send to client (%s): %s", channel.socket().getRemoteSocketAddress(), message));
    
            // Set channel to read
            channel.register(serverSelector, SelectionKey.OP_READ);
            // buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                channel.close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
            finally {
                key.cancel();
            }
        }
    }
    
    private void closeConnection(SelectionKey key, boolean isServer) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        SocketAddress remoteAddr = channel.socket().getRemoteSocketAddress();
        String closeInitiator = (isServer) ? "by server" : "by client";
        // log.info(String.format("Connection closed %s: %s", closeInitiator, remoteAddr));
        channel.close();
        key.cancel();
    }
}
