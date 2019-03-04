package model;

import java.util.Objects;

public class UserProfile {
    private transient long id;
    private String login;
    private String password;
    
    public UserProfile(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }
    
    public long getId() {
        return id;
    }
    
    public String getLogin() {
        return login;
    }
    
    public String getPassword() {
        return password;
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
