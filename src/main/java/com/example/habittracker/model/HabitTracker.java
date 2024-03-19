package com.example.habittracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "STATISTICS")
public class HabitTracker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    @OneToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @Column(name = "COMPLETION_DATE")
    private LocalDate completionDate;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private Status status;
}
