package model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private static final long serialVersionUID = -1450447348372659330L;
    
    private long id;
    private String login;
    private String firstName;
    private String lastName;
    private String middleName;
    
    // public User() {}
    
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
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getMiddleName() {
        return middleName;
    }
    
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }
    
    // public void copyFrom(User user) {
    //     setLogin(user.getLogin());
    //     setFirstName(user.getFirstName());
    //     setLastName(user.getLastName());
    //     setMiddleName(user.getMiddleName());
    // }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login) &&
                Objects.equals(firstName, user.firstName) &&
                Objects.equals(lastName, user.lastName) &&
                Objects.equals(middleName, user.middleName);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(login, firstName, lastName, middleName);
    }
}
