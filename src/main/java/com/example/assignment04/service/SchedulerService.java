package com.example.assignment04.service;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;

public interface SchedulerService {

    SchedulerResponseDto saveSchedule(SchedulerRequestDto schedulerRequestDto);

}
