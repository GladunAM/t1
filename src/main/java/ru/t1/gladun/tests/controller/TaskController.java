package ru.t1.gladun.tests.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.t1.gladun.tests.dto.TaskDto;
import ru.t1.gladun.tests.service.TaskService;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping(value = "/")
    public ResponseEntity createTask(@RequestBody TaskDto taskDto) {
        Long id = taskService.createTask(taskDto);
        return ResponseEntity.ok().body(new TaskDto(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity getTaskById(@PathVariable Long id) {
        TaskDto taskDto = taskService.getTaskById(id);
        return ResponseEntity.ok().body(taskDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTaskById(@RequestBody TaskDto taskDto, @PathVariable Long id) {
        taskService.updateTask(taskDto, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTaskById(@PathVariable Long id) {
        taskService.deleteTaskById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public ResponseEntity getAllTasks() {
        List<TaskDto> allTasks = taskService.getAllTasks();
        return ResponseEntity.ok().body(allTasks);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleException(EntityNotFoundException e) {
        return new ResponseEntity(
                "EntityNotFoundException", new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


}
