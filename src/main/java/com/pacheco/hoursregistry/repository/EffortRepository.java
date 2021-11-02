package com.pacheco.hoursregistry.repository;

import java.util.List;

import com.pacheco.hoursregistry.dto.EffortTaskDTO;
import com.pacheco.hoursregistry.model.Effort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EffortRepository extends JpaRepository<Effort, Long> {
    
    @Query("select new com.pacheco.hoursregistry.dto.EffortTaskDTO(" +
                "e.id, " +
                "e.initial, " +
                "e.termination, " +
                "e.task.resume, " +
                "e.task.done) " + 
            "from Effort e where e.termination = null")
    public List<Effort> findUndoneEfforts();

    @Query("select new com.pacheco.hoursregistry.dto.EffortTaskDTO(" +
                "e.id, " +
                "e.initial, " +
                "e.termination, " +
                "e.task.resume, " +
                "e.task.done) " + 
            "from Effort e where e.termination != null")
    public List<Effort> findDoneEfforts();

    @Query("select new com.pacheco.hoursregistry.dto.EffortTaskDTO(" +
                "e.id, " +
                "e.initial, " +
                "e.termination, " +
                "e.task.resume, " +
                "e.task.done) " + 
            "from Effort e")
    public List<EffortTaskDTO> findAllEfforts();

}
