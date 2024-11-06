package com.example.assignment04.controller;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.entity.Schedule;
import com.example.assignment04.service.SchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules")
public class SchedulerController {

    //최초로 설정한 SchedulerService가 애플리케이션이 끝날 때까지 지속됨
    private final SchedulerService schedulerService;

    //생성자
    public SchedulerController(SchedulerService schedulerService) {
        this.schedulerService = schedulerService;
    }

    //일정 생성
    @PostMapping
    public ResponseEntity<SchedulerResponseDto> createSchedule(@RequestBody SchedulerRequestDto dto) {

        //Service Layer 호출, 응답
        return new ResponseEntity<>(schedulerService.saveSchedule(dto), HttpStatus.CREATED);

    }




}
