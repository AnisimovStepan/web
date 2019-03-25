package com.crud.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "role", schema = "stepic_jdbc")
public class Role implements Serializable {
    private static final long serialVersionUID = -1771800871261688794L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 8)
    private RoleType role;
    
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
    
    public long getId() {
        return id;
    }
    
    public void setId(long id) {
        this.id = id;
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
        Role role1 = (Role) o;
        return role == role1.role;
    }
    
    @Override
    public int hashCode() {
        
        return Objects.hash(role);
    }
}
