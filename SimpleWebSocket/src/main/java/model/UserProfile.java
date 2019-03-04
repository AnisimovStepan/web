package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "stepic_websocket")
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -3696411127696301841L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "login", unique = true, length = 30)
    private String login;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(login, that.login);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(login);
    }
    
    @Override
    public String toString() {
        return String.format("UserProfile{ id = %d, login = '%s'", id, login);
    }
}
