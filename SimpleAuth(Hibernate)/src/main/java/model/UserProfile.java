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
@Table(name = "user", schema = "stepic_jdbc")
public class UserProfile implements Serializable {
    private static final long serialVersionUID = -3696411127696301841L;
    
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "login", unique = true, length = 30)
    private String login;
    
    @Column(name = "password", length = 30)
    private String password;
    
    // Worked if we have default constructor
    public UserProfile() {}
    
    public UserProfile(long id, String login, String password) {
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
    }

    public UserProfile(String login, String password) {
        // Mark like unavailable value
        this.setId(-1);
        this.setLogin(login);
        this.setPassword(password);
    }
    
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(login, that.login) &&
                Objects.equals(password, that.password);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(login, password);
    }
    
    @Override
    public String toString() {
        return String.format("UserProfile{ id = %d, login = '%s', password = '%s'", id, login, password);
    }
}
