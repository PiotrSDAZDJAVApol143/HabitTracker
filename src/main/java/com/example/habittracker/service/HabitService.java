package com.example.habittracker.service;

import com.example.habittracker.dto.HabitReadDto;
import com.example.habittracker.dto.HabitReqDto;
import com.example.habittracker.model.Habit;
import com.example.habittracker.repository.HabitRepository;
import com.example.habittracker.utils.mapper.HabitMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final HabitMapper habitMapper;

    public Habit addHabit(HabitReqDto request) {
        Habit habit = new Habit();
        habit.setHabitName(request.getName());
        habit.setDescription(request.getDescription());
        habit.setFrequency(request.getFrequency());
        habit.setFrequencyUnit(request.getFrequencyUnit());
        habitRepository.save(habit);
        return habit;
    }

    public HabitReadDto getHabit(Long id) {
        return habitRepository.findById(id)
                .map(habitMapper::toDto)
                .orElseThrow(()-> new EntityNotFoundException("Habit not found"));

    }

    public Habit updateHabit(Long id, HabitReqDto request) {
        Habit habit = habitRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Habit not found"));
        if(request.getName() !=null) {
            habit.setHabitName(request.getName());
        }
        if(request.getDescription() !=null){
            habit.setDescription(request.getDescription());
        }
        if(request.getFrequency() !=null){
            habit.setFrequency(request.getFrequency());
        }
        if(request.getFrequencyUnit() !=null){
            habit.setFrequencyUnit(request.getFrequencyUnit());
        }
        if(request.getActivities() != null){
            habit.getActivities().addAll(request.getActivities());
        }
        habitRepository.save(habit);
        return habit;

    }

    public void deleteHabit(Long id) {
        if(!habitRepository.existsById(id)){
            throw new EntityNotFoundException("Habit not found");
        }
        habitRepository.deleteById(id);
    }
}
