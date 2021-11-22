package com.pacheco.hoursregistry.repository;

import com.pacheco.hoursregistry.model.Task;

import java.util.List;

public interface TaskRepositoryQueries {

    public List<Task> findByQuery(Boolean done, String username);

}
