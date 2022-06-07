package com.example.todolist.list;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.config.Task;

import javax.persistence.*;

@Table
@Entity
@NoArgsConstructor
public class TaskItem {

    @Id
    @SequenceGenerator(
            name = "item_sequence",
            sequenceName = "item_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "item_sequence"
    )
    private Long id;
    private String subject;
    private String body;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public TaskItem(Long id, String subject, String body, TaskStatus status) {
        this.id = id;
        this.subject = subject;
        this.body = body;
        this.status = status;
    }

    public TaskItem(String subject, String body, TaskStatus status) {
        this.subject = subject;
        this.body = body;
        this.status = status;
    }
}
