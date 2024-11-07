package com.example.assignment04.controller;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;
import com.example.assignment04.entity.Schedule;
import com.example.assignment04.service.SchedulerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<SchedulerResponseDtoForSaveSchedule> createSchedule(@RequestBody SchedulerRequestDto dto) {

        //Service Layer 호출, 응답
        return new ResponseEntity<>(schedulerService.saveSchedule(dto), HttpStatus.CREATED);

    }

    //전체 조회
    @GetMapping
    public List<SchedulerResponseDto> findAllSchedules(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String updatedAt
    ) {
        return schedulerService.findAllSchedules(userName, updatedAt);
    }

    //단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> findScheduleById(@PathVariable Long id) {

        return new ResponseEntity<>(schedulerService.findScheduleById(id), HttpStatus.OK);
    }


    //단건 수정 기능
    @PutMapping("/{id}")
    public ResponseEntity<SchedulerResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody SchedulerRequestDto dto
    ){
        return new ResponseEntity<>(schedulerService.updateSchedule(id, dto.getUserName(), dto.getTitle(), dto.getContents(), dto.getPassword()), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody SchedulerRequestDto dto
    ) {
//        schedulerService.deleteMemo(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }


}
