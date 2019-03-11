package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "stepic_jdbc")
public class User implements Serializable {
    private static final long serialVersionUID = -1450447348372659330L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Column(name = "login", unique = true, nullable = false, length = 32)
    private String login;
    
    @Column(name = "password", nullable = false, length = 32)
    private String password;
    
    @Column(name = "first_name", nullable = false, length = 32)
    private String firstName;
    
    @Column(name = "last_name", nullable = false, length = 32)
    private String lastName;
    
    @Column(name = "middle_name", length = 32)
    private String middleName;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 8)
    private RoleType role;
    
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
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
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
    
    public RoleType getRole() {
        return role;
    }
    
    public void setRole(RoleType role) {
        this.role = role;
    }
    
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
