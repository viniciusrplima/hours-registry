package com.pacheco.hoursregistry.repository;

import java.util.List;

import com.pacheco.hoursregistry.model.Effort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EffortRepository extends JpaRepository<Effort, Long> {
    
    @Query("select e from Effort e where e.termination = null")
    public List<Effort> findUndoneEfforts();

    @Query("select e from Effort e where e.termination != null")
    public List<Effort> findDoneEfforts();

}
