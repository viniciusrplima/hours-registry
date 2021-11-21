package com.pacheco.hoursregistry.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    public Task(String resume, User user) {
        this.resume = resume;
        this.user = user;
    }

    public Effort updateUndoneEffortOrCreate() {
        return updateUndoneEffort().orElse(new Effort(LocalDateTime.now(), this));
    }

    private Optional<Effort> updateUndoneEffort() {
        Effort effort = null;

        for (Effort e : getEfforts()) {
            if (e.getTermination() == null) {
                effort = e;
                effort.setTermination(LocalDateTime.now());
                break;
            }
        }

        return Optional.ofNullable(effort);
    }

}
