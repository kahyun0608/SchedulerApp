package com.example.assignment04.service;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;

public interface SchedulerService {

    SchedulerResponseDtoForSaveSchedule saveSchedule(SchedulerRequestDto schedulerRequestDto);

}
