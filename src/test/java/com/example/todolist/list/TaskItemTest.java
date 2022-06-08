package com.example.todolist.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class TaskItemTest {

    TaskItem underTest;

    @BeforeEach
    void setUp() {
         underTest = new TaskItem(1L,
                "Unit test",
                "Get unit test done by 08/06/2022",
                TaskStatus.PENDING);
    }

    @Test
    void itShouldGetCorrectId() {
        //given

        //when
        final Long result = underTest.getId();

        //then
        assertEquals(1L, result);
    }

    @Test
    void itShouldSetNewId() throws NoSuchFieldException, IllegalAccessException {
        //given

        //when
        underTest.setId(2L);

        //then
        final Field field = underTest.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals(2L, field.get(underTest));

    }

    @Test
    void itShouldGetCorrectSubject() {
        assertEquals("Unit test", underTest.getSubject());
    }

    @Test
    void itShouldSetNewSubject() {
        underTest.setSubject("Integration test");
        assertEquals("Integration test", underTest.getSubject());
    }

    @Test
    void itShouldGetCorrectBody() {
        assertEquals("Get unit test done by 08/06/2022", underTest.getBody());
    }

    @Test
    void itShouldSetNewBody() {
        underTest.setBody("Get integration test done by 10/06/2022");
        assertEquals("Get integration test done by 10/06/2022", underTest.getBody());
    }

    @Test
    void itShouldGetCorrectStatus() {
        assertEquals(TaskStatus.PENDING, underTest.getStatus());
    }

    @Test
    void itShouldSetNewStatus() {
        underTest.setStatus(TaskStatus.DONE);
        assertEquals(TaskStatus.DONE, underTest.getStatus());
    }
}