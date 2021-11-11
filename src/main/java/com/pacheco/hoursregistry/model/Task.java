package com.pacheco.hoursregistry.model;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String resume;

    private Boolean done = Boolean.FALSE;

    @OneToMany(mappedBy = "task")
    private List<Effort> efforts;

    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Task(User user, String resume) {
        this.user = user;
        this.resume = resume;
    }

}
