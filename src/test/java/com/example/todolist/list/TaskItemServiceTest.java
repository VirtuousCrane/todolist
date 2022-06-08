package com.example.todolist.list;

import org.h2.util.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void updateFindByIdTest() {
        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "PENDING";
        TaskStatus statusEnum = TaskStatus.valueOf(status);

        //when
        when(taskRepository.findById(id))
                .thenReturn(Optional.of(new TaskItem(id, subject, body, statusEnum)));
        TaskItem result = taskRepository.findById(id)
                .orElse(null);

        //then
        assertEquals(id, result.getId());
        assertEquals(subject, result.getSubject());
        assertEquals(body, result.getBody());
        assertEquals(statusEnum, result.getStatus());
    }

    @Test
    @Disabled
    void updateTask() {

        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "DONE";
        TaskStatus statusEnum = TaskStatus.valueOf(status);

        when(taskRepository.findById(id))
                .thenReturn(Optional.of(new TaskItem(id, "A", "B", TaskStatus.PENDING)));

        //when
        underTest.updateTask(id, subject, body, status);

        //then
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        verify(taskItem).setSubject(subjectCaptor.capture());
        assertEquals(subject, subjectCaptor.getValue());

        verify(taskItem).setBody(body);
        verify(taskItem).setStatus(statusEnum);

    }

    @Test
    void deleteTask() {
        long id = 1;
        given(taskRepository.existsById(id)).willReturn(true);

        underTest.deleteTask(id);

        verify(taskRepository).deleteById(id);
    }

    @Test
    void deleteWillThrowTask() {
        //given
        long id = 1;

        //when
        assertThatThrownBy(() -> underTest.deleteTask(id))
                .isInstanceOf(IllegalStateException.class);

    }


}