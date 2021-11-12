package com.pacheco.hoursregistry.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "auth_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;

    private String githubToken;

    @OneToMany(mappedBy = "user")
    private List<Task> tasks;

    public User(String username, String password, String githubToken) {
        this.username = username;
        this.password = password;
        this.githubToken = githubToken;
    }
}
