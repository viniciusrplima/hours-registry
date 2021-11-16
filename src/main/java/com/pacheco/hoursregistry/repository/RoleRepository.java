package com.pacheco.hoursregistry.repository;

import com.pacheco.hoursregistry.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    public List<Role> findByNameIn(List<String> roles);

}
