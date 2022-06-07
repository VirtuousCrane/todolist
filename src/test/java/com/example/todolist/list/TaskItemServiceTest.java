package com.example.todolist.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskItemServiceTest {

    @Mock
    private TaskRepository taskRepository;

    private TaskItemService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TaskItemService(taskRepository);
    }
    @Test
    void canGetAllTask(){
        underTest.getAllTasks();
        verify(taskRepository).findAll();
    }

    @Test
    void getTaskById() {
        long id = 1;
        final TaskItem taskItem = new TaskItem(id,"Test subject", "Test body", TaskStatus.DONE);
        doReturn(Optional.of(taskItem)).when(taskRepository).findById(id);
        final TaskItem taskService = underTest.getTaskById(id);
        assertEquals(taskItem.getSubject(), taskService.getSubject());
        assertEquals(taskItem.getBody(), taskService.getBody());
        assertEquals(taskItem.getStatus(), taskService.getStatus());
    }
    @Test
    void canAddTask() {
        TaskItem taskItem = new TaskItem("Test subject",
                "Test Body",
                TaskStatus.DONE);

        underTest.addTask(taskItem);

        ArgumentCaptor<TaskItem> taskItemArgumentCaptor = ArgumentCaptor.forClass(TaskItem.class);
        verify(taskRepository).save(taskItemArgumentCaptor.capture());
        TaskItem capturedTaskItem = taskItemArgumentCaptor.getValue();
        assertThat(capturedTaskItem).isEqualTo(taskItem);
    }

    @Test
    @Rollback(value = false)
    void updateTask() {

    }

    @Test
    void deleteTask() {
        long id = 1;
        given(taskRepository.existsById(id)).willReturn(true);

        underTest.deleteTask(id);

        verify(taskRepository).deleteById(id);
    }


}