package com.example.SpringProjectsManagement.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tasks")
public class Task implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "taskId")
    private long id;

    @Column(name = "projectId")
    private long projectId;

    @Column(name = "content")
    private String content;

    @Column(name = "isChecked")
    private boolean isChecked;

    public Task() {

    }

    public Task(long projectId, String content, boolean checked) {
        this.projectId = projectId;
        this.content = content;
        this.isChecked = checked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
