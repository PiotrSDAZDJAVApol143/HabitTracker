package com.example.habittracker.dto;


import lombok.*;



@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class ReminderReqDto {
    private Long habitId;
    private String message;
    private String reminderTime;  // zmieniony typ na String dla formattera

}
