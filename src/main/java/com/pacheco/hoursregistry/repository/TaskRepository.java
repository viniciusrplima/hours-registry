package com.pacheco.hoursregistry.repository;

import java.util.List;
import java.util.Optional;

import com.pacheco.hoursregistry.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>, TaskRepositoryQueries {

    public Optional<Task> findTaskByUserUsernameAndId(String username, Long id);

    public void deleteByUserUsernameAndId(String username, Long id);

}