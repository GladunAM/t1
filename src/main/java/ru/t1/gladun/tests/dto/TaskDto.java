package ru.t1.gladun.tests.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ru.t1.gladun.tests.entity.TaskStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private TaskStatus status;

    public TaskDto(Long id, String title, String description, TaskStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public TaskDto(Long id) {
        this.id = id;
    }

    public TaskDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
