package com.example.SpringProjectsManagement.repository;


import com.example.SpringProjectsManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ITaskRepository extends JpaRepository<Task, Long> {

//    @Query("SELECT task FROM tasks task WHERE task.projectId = ?1")
    List<Task> findByProjectId(long projectId);
}
