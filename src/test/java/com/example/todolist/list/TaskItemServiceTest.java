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
        // Given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "PENDING";
        TaskStatus statusEnum = TaskStatus.valueOf(status);

        final TaskItem returnTask = new TaskItem(id, subject, body, statusEnum);

        // When
        when(taskRepository.findById(id))
                .thenReturn(Optional.of(returnTask));
        final TaskItem task = underTest.getTaskById(id);

        // Then
        assertEquals(id, task.getId());
        assertEquals(subject, task.getSubject());
        assertEquals(body, task.getBody());
        assertEquals(statusEnum, task.getStatus());
    }

    @Test
    void getTaskByIdDoesNotExist() {

        //Given
        Long id = 1L;

        //when
        assertThatThrownBy(() -> underTest.getTaskById(id))
                .isInstanceOf(IllegalStateException.class);

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
    void updateTask() {

        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "PENDING";

        String newSubject = "New Subject";
        String newBody = "New Body";
        String newStatus = "DONE";

        TaskStatus statusEnum = TaskStatus.valueOf(status);
        TaskStatus newStatusEnum = TaskStatus.valueOf(newStatus);

        TaskItem item = new TaskItem(id, subject, body, statusEnum);

        //when
        when(taskRepository.findById(id))
                .thenReturn(Optional.of(item));

        underTest.updateTask(id, newSubject, newBody, newStatus);

        //then
        assertThat(item.getSubject()).isEqualTo(newSubject);
        assertThat(item.getBody()).isEqualTo(newBody);
        assertThat(item.getStatus()).isEqualTo(newStatusEnum);

    }

    @Test
    void updateTestNull() {

        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "PENDING";
        TaskStatus statusEnum = TaskStatus.valueOf(status);

        String newSubject = null;
        TaskItem item = new TaskItem(id, subject, body, statusEnum);

        //when
        when(taskRepository.findById(id))
                .thenReturn(Optional.of(item));

        underTest.updateTask(id, null, null, null);

        //then
        assertThat(item.getSubject()).isEqualTo(subject);
        assertThat(item.getBody()).isEqualTo(body);
        assertThat(item.getStatus()).isEqualTo(statusEnum);

    }

    @Test
    void updateTestSame() {

        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "PENDING";

        TaskStatus statusEnum = TaskStatus.valueOf(status);

        TaskItem item = new TaskItem(id, subject, body, statusEnum);

        //when
        when(taskRepository.findById(id))
                .thenReturn(Optional.of(item));

        underTest.updateTask(id, subject, body, status);

        //then
        assertThat(item.getSubject()).isEqualTo(subject);
        assertThat(item.getBody()).isEqualTo(body);
        assertThat(item.getStatus()).isEqualTo(statusEnum);

    }

    @Test
    void updateTestEmpty() {

        //given
        Long id = 1L;
        String subject = "Subject";
        String body = "Body";
        String status = "PENDING";

        TaskStatus statusEnum = TaskStatus.valueOf(status);

        TaskItem item = new TaskItem(id, subject, body, statusEnum);

        //when
        when(taskRepository.findById(id))
                .thenReturn(Optional.of(item));

        underTest.updateTask(id, "", "", status);

        //then
        assertThat(item.getSubject()).isEqualTo(subject);
        assertThat(item.getBody()).isEqualTo("");
        assertThat(item.getStatus()).isEqualTo(statusEnum);

    }

    @Test
    void updateCheckDoesNotExist() {

        //Given
        Long id = 1L;

        //when
        assertThatThrownBy(() -> underTest.updateTask(id, "subject", "body", "DONE"))
                .isInstanceOf(IllegalStateException.class);

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