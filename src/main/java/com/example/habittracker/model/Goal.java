package com.example.habittracker.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Table(name = "GOAL")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "GOAL_NAME", length = 128, nullable = false)
    private String goalName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "GOAL_CATEGORY")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "START_DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "END_DATE")
    private LocalDate endDate;

}
