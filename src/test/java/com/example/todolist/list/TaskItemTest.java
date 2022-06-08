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
        final String result = underTest.getSubject();
        assertEquals("Unit test", underTest.getSubject());
    }

    @Test
    void itShouldSetNewSubject() throws NoSuchFieldException, IllegalAccessException {
        underTest.setSubject("Integration test");
        final Field field = underTest.getClass().getDeclaredField("subject");
        field.setAccessible(true);
        assertEquals("Integration test", field.get(underTest));
    }

    @Test
    void itShouldGetCorrectBody() {
        final String result = underTest.getBody();
        assertEquals("Get unit test done by 08/06/2022", result);
    }

    @Test
    void itShouldSetNewBody() throws NoSuchFieldException, IllegalAccessException {
        underTest.setBody("Get integration test done by 10/06/2022");
        final Field field = underTest.getClass().getDeclaredField("body");
        field.setAccessible(true);
        assertEquals("Get integration test done by 10/06/2022", field.get(underTest));
    }

    @Test
    void itShouldGetCorrectStatus() {

        final TaskStatus result = underTest.getStatus();
        assertEquals(TaskStatus.PENDING, underTest.getStatus());
    }

    @Test
    void itShouldSetNewStatus() throws NoSuchFieldException, IllegalAccessException {
        underTest.setStatus(TaskStatus.DONE);
        final Field field = underTest.getClass().getDeclaredField("status");
        field.setAccessible(true);
        assertEquals(TaskStatus.DONE, field.get(underTest));
    }
}