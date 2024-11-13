package ru.t1.gladun.tests.service;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.t1.gladun.tests.dto.TaskDto;
import ru.t1.gladun.tests.entity.Task;
import ru.t1.gladun.tests.repository.TaskRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public final Function<Task, TaskDto> toBookDto = (Task task) -> new TaskDto(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getStatus()
    );

    public Long createTask(TaskDto taskDto) {
        Task task = taskRepository.save(new Task(
                taskDto.getId(),
                taskDto.getTitle(),
                taskDto.getDescription(),
                taskDto.getStatus()));
        return task.getId();
    }

    public TaskDto getTaskById(Long id) {
        TaskDto taskDto = toBookDto.apply(taskRepository.findById(id).orElseThrow(EntityNotFoundException::new));

        return taskDto;
    }

    public void updateTask(TaskDto taskDto, Long id) {
        Optional<Task> taskOpt = taskRepository.findById(id);
        if (taskOpt.isEmpty()) {
            throw new EntityNotFoundException();
        }
        Task task = taskOpt.get();
        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setStatus(taskDto.getStatus());
        taskRepository.save(task);
    }

    public void deleteTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    public List<TaskDto> getAllTasks() {
        List<TaskDto> tasks = taskRepository.findAll().stream().map(toBookDto).toList();

        return tasks;
    }
}
