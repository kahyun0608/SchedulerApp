package com.example.assignment04.repository;

import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;
import com.example.assignment04.entity.Schedule;

import java.util.List;

public interface SchedulerRepository {

    SchedulerResponseDtoForSaveSchedule saveSchedule(Schedule schedule);

    List<SchedulerResponseDto> findAllSchedules(String userName, String updatedAt);

}
