package com.example.todolist.list;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TaskItemControllerTest {
    @Mock private TaskItemService taskItemService;
    private TaskItemController underTest;
    private TaskStatus status;

    @BeforeEach
    void setUp(){
        underTest = new TaskItemController(taskItemService);
    }

    @Test()
    void getAllTasks() {
        underTest.getAllTasks();
        verify(taskItemService).getAllTasks();
    }

    @Test()
    void getTaskById() {
        //given
        long id = 10;

        //when
        underTest.getTaskById(id);

        //then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        verify(taskItemService).getTaskById(idCaptor.capture());
        assertEquals(id, idCaptor.getValue());
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

        verify(taskItemService)
                .addTask(taskItemArgumentCaptor.capture());

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

        HashMap<String, String> map = new HashMap();
        map.put("subject", subject);
        map.put("body", body);
        map.put("status", status);


        //when
        underTest.updateTask(id, map);

        //then
        ArgumentCaptor<Long> idCaptor = ArgumentCaptor.forClass(Long.class);
        ArgumentCaptor<String> subjectCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> statusCaptor = ArgumentCaptor.forClass(String.class);

        verify(taskItemService).updateTask(idCaptor.capture(), subjectCaptor.capture(), bodyCaptor.capture(), statusCaptor.capture());
        assertEquals(id, idCaptor.getValue());
        assertEquals(subject, subjectCaptor.getValue());
        assertEquals(body, bodyCaptor.getValue());
        assertEquals(status, statusCaptor.getValue());
    }

    @Test
    void deleteTask() {
        long id = 10;
        //when
        underTest.deleteTask(id);
        //then
        verify(taskItemService).deleteTask(id);
    }
}
