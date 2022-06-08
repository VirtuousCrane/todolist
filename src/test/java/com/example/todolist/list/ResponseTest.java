package com.example.todolist.list;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    void getStatusCode() throws NoSuchFieldException, IllegalAccessException {

        //given
        final Response response = new Response();
        final Field field = response.getClass().getDeclaredField("statusCode");
        field.setAccessible(true);
        field.set(response, 200);

        //when
        final int result = response.getStatusCode();

        //then
        assertEquals(200, result);

    }

    @Test
    void getBody() throws NoSuchFieldException, IllegalAccessException {

        //given
        final Response response = new Response();
        final Field field = response.getClass().getDeclaredField("body");
        field.setAccessible(true);
        field.set(response, "Body");

        //when
        final String result = response.getBody();

        //then
        assertEquals("Body", result);

    }

    @Test
    void setStatusCode() throws NoSuchFieldException, IllegalAccessException {

        //given
        final Response response = new Response();

        //when
        response.setStatusCode(200);

        //then
        final Field field = response.getClass().getDeclaredField("statusCode");
        field.setAccessible(true);
        assertEquals(200, field.get(response));

    }

    @Test
    void setBody() throws NoSuchFieldException, IllegalAccessException {

        //given
        final Response response = new Response();

        //when
        response.setBody("Body");

        //then
        final Field field = response.getClass().getDeclaredField("body");
        field.setAccessible(true);
        assertEquals(field.get(response), "Body");

    }
}