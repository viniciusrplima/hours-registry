package com.pacheco.hoursregistry.repository;

import java.util.List;

import com.pacheco.hoursregistry.model.Task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    @Query("select t from Task t where t.done = false")
    public List<Task> findUndoneTasks();

}