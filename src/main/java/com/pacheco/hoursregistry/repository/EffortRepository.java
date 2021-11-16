package com.pacheco.hoursregistry.repository;

import java.util.List;
import java.util.Optional;

import com.pacheco.hoursregistry.dto.EffortTaskDTO;
import com.pacheco.hoursregistry.model.Effort;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EffortRepository extends JpaRepository<Effort, Long> {
    
    static final String baseQuery = ""+
        "select new com.pacheco.hoursregistry.dto.EffortTaskDTO(" +
            "e.id, " +
            "e.initial, " +
            "e.termination, " +
            "e.task.resume, " +
            "e.task.done) " + 
        "from Effort e where e.task.user.username = :username";

    @Query(baseQuery + " and e.termination = null")
    public List<EffortTaskDTO> findUndoneEfforts(String username);

    @Query(baseQuery + " and e.termination != null")
    public List<EffortTaskDTO> findDoneEfforts(String username);

    @Query(baseQuery)
    public List<EffortTaskDTO> findAllEfforts(String username);

    @Query(baseQuery + " and e.id = :effortId")
    public Optional<EffortTaskDTO> findEffortTaskById(String username, Long effortId);

}
