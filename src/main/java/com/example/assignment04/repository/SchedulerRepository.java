package com.example.assignment04.repository;

import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.entity.Schedule;

public interface SchedulerRepository {

    SchedulerResponseDto saveSchedule(Schedule schedule);

}
