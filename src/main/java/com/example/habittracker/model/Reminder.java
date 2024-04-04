package com.example.habittracker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
@Entity
@Getter
@Setter
@Table(name = "REMINDER")
public class Reminder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "REMINDER_TIME")
    private LocalDateTime reminderTime;

    @ManyToOne
    @JoinColumn(name = "habit_id")
    private Habit habit;

    private Long goalId;
}
