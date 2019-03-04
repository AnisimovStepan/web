package services;

import dao.MessageDao;
import model.Message;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import websocket.ChatWebSocket;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class ChatService {
    private Set<ChatWebSocket> webSockets;
    // For db working
    private MessageDao messageDao;
    
    public ChatService() {
    // public ChatService(MessageDao messageDao) {
        this.webSockets = Collections.newSetFromMap(new ConcurrentHashMap<>());
        // this.messageDao = messageDao;
    }
    
    public void sendMessage(String data) {
        for (ChatWebSocket user : webSockets) {
            try {
                user.sendString(data);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void add(ChatWebSocket webSocket) {
        webSockets.add(webSocket);
    }
    
    public void remove(ChatWebSocket webSocket) {
        webSockets.remove(webSocket);
    }
}
