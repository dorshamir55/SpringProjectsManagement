package com.example.SpringProjectsManagement.repository;


import com.example.SpringProjectsManagement.model.Project;
import com.example.SpringProjectsManagement.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {
//    List<Task> findByProjectId(long projectId);
}
