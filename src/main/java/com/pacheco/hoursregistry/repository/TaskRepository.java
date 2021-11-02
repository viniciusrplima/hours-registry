package com.pacheco.hoursregistry.repository;

import java.util.List;

import com.pacheco.hoursregistry.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    public List<Task> findTasksByDone(Boolean done);

}