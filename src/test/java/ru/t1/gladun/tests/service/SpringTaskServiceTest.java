package ru.t1.gladun.tests.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.t1.gladun.tests.AbstractContainerBasetest;
import ru.t1.gladun.tests.dto.TaskDto;
import ru.t1.gladun.tests.entity.Task;
import ru.t1.gladun.tests.entity.TaskStatus;
import ru.t1.gladun.tests.repository.TaskRepository;

import java.util.List;

@SpringBootTest
class SpringTaskServiceTest extends AbstractContainerBasetest {
    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void setUp() {
        Task mockTask_1 = new Task();
        mockTask_1.setTitle("Mock_Title_1");
        mockTask_1.setDescription("Mock_Description_1");
        mockTask_1.setStatus(TaskStatus.rejected);

        Task mockTask_2 = new Task();
        mockTask_2.setTitle("Mock_Title_2");
        mockTask_2.setDescription("Mock_Description_2");
        mockTask_2.setStatus(TaskStatus.completed);

        Task mockTask_3 = new Task();
        mockTask_3.setTitle("Mock_Title_3");
        mockTask_3.setDescription("Mock_Description_3");
        mockTask_3.setStatus(TaskStatus.closed);

        taskRepository.deleteAll();
        taskRepository.save(mockTask_1);
        taskRepository.save(mockTask_2);
        taskRepository.save(mockTask_3);
    }

    @Test
    @DisplayName("Тест получения всех заданий")
    void testGetAllTasks() {
        List<TaskDto> tasks = taskService.getAllTasks();

        Assertions.assertEquals(taskRepository.count(), tasks.size());
    }

    @Test
    @DisplayName("Тест получения задания по идентификатору")
    void testGetTaskById() {

        Long id_db = taskService.getAllTasks().get(1).getId();
        TaskDto taskDto = taskService.getTaskById(id_db);
        Assertions.assertNotNull(taskDto);
        Assertions.assertEquals("Mock_Title_2", taskDto.getTitle());
        Assertions.assertEquals("Mock_Description_2", taskDto.getDescription());
        Assertions.assertEquals(TaskStatus.completed, taskDto.getStatus());
    }

    @Test
    @DisplayName("Тест получения ислючения при запросе задания по несуществующему идентификатору")
    void testGetTaskByIdThrowsException() {
        Integer size = taskService.getAllTasks().size() - 1;
        Long id_db = taskService.getAllTasks().get(size).getId();
        Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(id_db + 1));
    }

    @Test
    @DisplayName("Тест создания задания")
    void testCreateTask() {
        TaskDto mockTaskDto = new TaskDto();
        mockTaskDto.setTitle("Mock_Title_3");
        mockTaskDto.setDescription("Mock_Description_3");
        mockTaskDto.setStatus(TaskStatus.closed);

        Long id = taskService.createTask(mockTaskDto);
        TaskDto taskDto = taskService.getTaskById(id);

        Assertions.assertNotNull(id);
        Assertions.assertEquals(mockTaskDto.getTitle(), taskDto.getTitle());
        Assertions.assertEquals(mockTaskDto.getDescription(), taskDto.getDescription());
        Assertions.assertEquals(mockTaskDto.getStatus(), taskDto.getStatus());
    }

    @Test
    @DisplayName("Тест изменения задания")
    void testUpdateTask() {
        TaskDto mockTaskDto = new TaskDto();
        mockTaskDto.setTitle("Mock_Title_Update");
        mockTaskDto.setDescription("Mock_Description_Update");
        mockTaskDto.setStatus(TaskStatus.on_verification);

        Long id_db = taskService.getAllTasks().get(2).getId();
        taskService.updateTask(mockTaskDto, id_db);

        TaskDto oldTaskDto = taskService.getTaskById(id_db);

        Assertions.assertNotNull(oldTaskDto);
        Assertions.assertEquals(mockTaskDto.getTitle(), oldTaskDto.getTitle());
        Assertions.assertEquals(mockTaskDto.getDescription(), oldTaskDto.getDescription());
        Assertions.assertEquals(mockTaskDto.getStatus(), oldTaskDto.getStatus());
    }

    @Test
    @DisplayName("Тест получения ислючения при попытке изменить задание по несуществующему идентификатору")
    void testUpdateTaskThrowsException() {
        Integer size = taskService.getAllTasks().size() - 1;
        Long id_db = taskService.getAllTasks().get(size).getId();

        TaskDto mockTaskDto = new TaskDto();

        mockTaskDto.setTitle("Mock_Title_UpdateNew");
        mockTaskDto.setDescription("Mock_Description_UpdateNew");
        mockTaskDto.setStatus(TaskStatus.for_execution);
        Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.updateTask(mockTaskDto, id_db + 1));
    }


    @Test
    @DisplayName("Тест удаления задания")
    void testDeleteTaskById() {
        Long id_db = taskService.getAllTasks().get(1).getId();
        taskService.deleteTaskById(id_db);

        Assertions.assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(id_db));
    }
}