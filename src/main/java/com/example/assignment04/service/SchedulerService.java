package com.example.assignment04.service;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;

import java.util.List;

public interface SchedulerService {

    SchedulerResponseDtoForSaveSchedule saveSchedule(SchedulerRequestDto schedulerRequestDto);

    List<SchedulerResponseDto> findAllSchedules(String userName, String updatedAt);

    SchedulerResponseDto findScheduleById(Long id);

    SchedulerResponseDto updateSchedule(Long id, String user, String title, String contents, String password);

    void deleteSchedule(Long id, String password);
}
