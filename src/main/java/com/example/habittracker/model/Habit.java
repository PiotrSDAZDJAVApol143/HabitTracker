package com.example.habittracker.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;


import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
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

    @OneToMany(mappedBy = "habit", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Activity> activities = new ArrayList<>();

    @Column(name = "PROGRESS")
    private String progress;

    @ManyToOne
    @JoinColumn(name = "goal_id")
    private Goal goal;

    @ManyToOne
    @JoinColumn(name = "statistics_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Statistics statistics;

    @OneToMany(mappedBy = "habit")
    private List<Reminder> reminders = new ArrayList<>();
}
