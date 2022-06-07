package com.example.todolist.list;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.hibernate.mapping.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskItemControllerTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskItemService taskItemService;
    private TaskItemController underTest;

    private TaskStatus status;

    @BeforeEach
    void setUp(){
        taskItemService = new TaskItemService(taskRepository);
        underTest = new TaskItemController(taskItemService);
    }

    @Test
    void getAllTasks() {
        //when
        underTest.getAllTasks();
        //then
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById() {
        //when
        long id = 10;
        given(taskRepository.existsById(id))
                .willReturn(true);
        underTest.getTaskById(id);
        //then
        verify(taskRepository).findById(id);
    }

    @Test
    void addTask() {
        //given
        TaskItem taskItem = new TaskItem(
                1L,
                "Testing Subject",
                "Testing Body",
                status.PENDING
        );
        //when
        underTest.addTask(taskItem);
        //then
        ArgumentCaptor<TaskItem> taskItemArgumentCaptor =
                ArgumentCaptor.forClass(TaskItem.class);

        verify(taskRepository)
                .save(taskItemArgumentCaptor.capture());

        TaskItem capturedTaskItem = taskItemArgumentCaptor.getValue();

        assertThat(capturedTaskItem).isEqualTo(taskItem);
    }

    @Test
    void updateTask() {
        //given
        TaskItem initialTaskItem = new TaskItem(
                1L,
                "Testing Subject",
                "Testing Body",
                status.PENDING
        );

        HashMap<String, String> updateHashMap = new HashMap<>();
        updateHashMap.put("subject","Update Testing Subject");
        updateHashMap.put("body", "Update Testing Body");
        updateHashMap.put("status", "DONE");

        TaskItem updateTaskItem = new TaskItem(
                1L,
                "Update Testing Subject",
                "Update Testing Body",
                status.DONE
        );
        underTest.addTask(initialTaskItem);
        //when
        underTest.updateTask(1L, updateHashMap);
        //then
        ArgumentCaptor<TaskItem> taskItemArgumentCaptor =
                ArgumentCaptor.forClass(TaskItem.class);

        verify(taskRepository)
                .save(taskItemArgumentCaptor.capture());

        TaskItem capturedTaskItem = taskItemArgumentCaptor.getValue();

        assertThat(capturedTaskItem).isEqualTo(updateTaskItem);
    }

    @Test
    void deleteTask() {
    }
}