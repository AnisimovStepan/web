package com.userCrudSpring.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", schema = "stepic_jdbc",
            joinColumns = { @JoinColumn(name = "user_fk", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "role_fk", referencedColumnName = "id") })
    private List<Role> roles = new ArrayList<>();
    
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
    
    public List<Role> getRoles() {
        return roles;
    }
    
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    // public RoleType getRole() {
    //     return role;
    // }
    //
    // public void setRole(RoleType role) {
    //     this.role = role;
    // }
    
    // @Override
    // public boolean equals(Object o) {
    //     if (this == o) return true;
    //     if (o == null || getClass() != o.getClass()) return false;
    //     User user = (User) o;
    //     return Objects.equals(login, user.login) &&
    //             Objects.equals(firstName, user.firstName) &&
    //             Objects.equals(lastName, user.lastName) &&
    //             Objects.equals(middleName, user.middleName);
    // }
    //
    // @Override
    // public int hashCode() {
    //     return Objects.hash(login, firstName, lastName, middleName);
    // }
}
