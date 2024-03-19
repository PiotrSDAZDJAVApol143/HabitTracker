package com.example.habittracker.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "HABIT")
public class Habit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "HABIT_NAME", nullable = false)
    private String habitName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FREQUENCY")
    private Integer frequency;

    @Enumerated(EnumType.STRING)
    @Column(name = "FREQUENCY_UNIT")
    private FrequencyUnit frequencyUnit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "habit")
    private List<Activity> activities = new ArrayList<>();


}
