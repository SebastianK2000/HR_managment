package org.example.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    // Getters

    public Long getId() {
        return id;
    }

    public Set<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    //Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public void setName(String name) {
        this.name = name;
    }
}
