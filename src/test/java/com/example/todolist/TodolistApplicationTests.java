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

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TodolistApplicationTests {
	@Mock
	private TaskRepository taskRepository;

	private TaskItemService underTest;

	@BeforeEach
	void setUp() {
		underTest = new TaskItemService(taskRepository);
	}
	void canGetAllTask(){
		underTest.getAllTasks();
		verify(taskRepository).findAll();
	}
	@Test
	void canAddTask() {
		TaskItem taskItem = new TaskItem("Test subject",
				"Test Body",
				TaskStatus.DONE);
		
	}

	@Test
	void updateTask() {
	}

	@Test
	void deleteTask() {
	}

}
