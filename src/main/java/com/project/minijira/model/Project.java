package com.project.minijira.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;
    @Column(name = "created_at", nullable = false)

    private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false)

    private LocalDateTime updatedAt;
}

