package com.example.todolist;

import com.example.todolist.list.TaskItem;
import com.example.todolist.list.TaskItemService;
import com.example.todolist.list.TaskRepository;
import com.example.todolist.list.TaskStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;


class TodolistApplicationTests {
    @Test
    void main() {
        TodolistApplication.main(new String[] {});
    }
}
