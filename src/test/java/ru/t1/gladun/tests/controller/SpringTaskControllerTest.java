package ru.t1.gladun.tests.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.t1.gladun.tests.AbstractContainerBasetest;
import ru.t1.gladun.tests.entity.Task;
import ru.t1.gladun.tests.entity.TaskStatus;
import ru.t1.gladun.tests.repository.TaskRepository;


import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SpringTaskControllerTest extends AbstractContainerBasetest {
    @Autowired
    private MockMvc mockMvc;

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

        taskRepository.deleteAll();
        taskRepository.save(mockTask_1);
        taskRepository.save(mockTask_2);
    }

    @Test
    @DisplayName("Тест получения всех заданий")
    void getAllTasks() throws Exception {
        mockMvc.perform(
                        get("/tasks/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].title", is("Mock_Title_1")))
                .andExpect(jsonPath("$[0].description", is("Mock_Description_1")))
                .andExpect(jsonPath("$[0].status", is(TaskStatus.rejected.name())))
                .andExpect(jsonPath("$[1].title", is("Mock_Title_2")))
                .andExpect(jsonPath("$[1].description", is("Mock_Description_2")))
                .andExpect(jsonPath("$[1].status", is(TaskStatus.completed.name())));

    }

    @Test
    @DisplayName("Тест создания задания")
    void createTask() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Mock_Title_New");
        params.put("description", "Mock_Description_New");
        params.put("status", TaskStatus.closed.name());
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>() {
        }.getType();
        String gsonString = gson.toJson(params, gsonType);
        mockMvc.perform(
                        post("/tasks/").contentType(MediaType.APPLICATION_JSON).content(gsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", anything()));
    }

    @Test
    @DisplayName("Тест получения задания по идентификатору")
    void getTaskById() throws Exception {
        Long id_db = taskRepository.findAll().get(0).getId();
        mockMvc.perform(
                        get("/tasks/" + id_db))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.title", is("Mock_Title_1")))
                .andExpect(jsonPath("$.description", is("Mock_Description_1")))
                .andExpect(jsonPath("$.status", is(TaskStatus.rejected.name())));
    }

    @Test
    @DisplayName("Тест изменения задания")
    void updateTaskById() throws Exception {
        Map<String, Object> params = new HashMap<>();
        params.put("title", "Mock_Title_Updated");
        params.put("description", "Mock_Description_Updated");
        params.put("status", TaskStatus.closed.name());
        Gson gson = new Gson();
        Type gsonType = new TypeToken<HashMap>() {
        }.getType();
        String gsonString = gson.toJson(params, gsonType);
        Long id_db = taskRepository.findAll().get(0).getId();
        mockMvc.perform(
                        put("/tasks/" + id_db).contentType(MediaType.APPLICATION_JSON).content(gsonString))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Тест удаления задания")
    void deleteTaskById() throws Exception {
        Long id_db = taskRepository.findAll().get(0).getId();
        mockMvc.perform(
                        delete("/tasks/" + id_db))
                .andExpect(status().isOk());
    }

}