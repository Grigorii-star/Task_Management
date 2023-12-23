package com.test.taskmanager.entity;

import com.test.taskmanager.enums.Priority;
import com.test.taskmanager.enums.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;

@Getter
@Setter
@EqualsAndHashCode(exclude = "id")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "task", cascade = CascadeType.ALL)
    private Collection<Comment> comments;

    @OneToMany(mappedBy = "task")
    private Collection<User> performers;

}
