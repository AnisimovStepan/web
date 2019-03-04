package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "message", schema = "stepic_websocket")
public class Message implements Serializable {
    private static final long serialVersionUID = 2870238382509903617L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "message", length = 256)
    private String message;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile user;
    
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public UserProfile getUser() {
        return user;
    }
    
    public void setUser(UserProfile user) {
        this.user = user;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message1 = (Message) o;
        return Objects.equals(message, message1.message) &&
                Objects.equals(user, message1.user);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(message, user);
    }
    
    @Override
    public String toString() {
        return String.format("Message{  message = %s, userName = %s }", message, user.getLogin());
    }
}
