package com.example.todolist.list;

import org.h2.util.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskItemServiceTest {

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskItem taskItem;

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
    void updateTask() {
        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "DONE";
        TaskStatus statusAsEnum = TaskStatus.valueOf(status);

        TaskItem task = new TaskItem(id, subject, body, statusAsEnum);
        Optional<TaskItem> taskAsOptional = Optional.of(task);

        //when
        when(taskRepository.findById(id))
                .thenReturn(taskAsOptional);

        when(Objects.equals(taskItem.getSubject(), subject))
                .thenReturn(false);

        when(Objects.equals(taskItem.getBody(), body))
                .thenReturn(false);

        when(Objects.equals(taskItem.getStatus(), status))
                .thenReturn(false);

        underTest.updateTask(id, subject, body, status);

        //then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(taskRepository).findById(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());

        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskItem).setSubject(subjectCaptor.capture());
        assertEquals(subject, subjectCaptor.getValue());

        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskItem).setSubject(bodyCaptor.capture());
        assertEquals(body, bodyCaptor.getValue());

        ArgumentCaptor<TaskStatus> statusCaptor = ArgumentCaptor.forClass(TaskStatus.class);
        verify(taskItem).setStatus(statusCaptor.capture());
        assertEquals(statusAsEnum, statusCaptor.getValue());
    }

    @Test
    void deleteTask() {
        long id = 1;
        given(taskRepository.existsById(id)).willReturn(true);

        underTest.deleteTask(id);

        verify(taskRepository).deleteById(id);
    }


}