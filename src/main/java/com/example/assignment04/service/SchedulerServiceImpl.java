package com.example.assignment04.service;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;
import com.example.assignment04.entity.Schedule;
import com.example.assignment04.repository.SchedulerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService{

    //repository 사용위해 필드와 생성자 지정
    private final SchedulerRepository schedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository) {
        this.schedulerRepository = schedulerRepository;
    }

    //일정 생성 API
    @Override
    public SchedulerResponseDtoForSaveSchedule saveSchedule(SchedulerRequestDto dto) {
        //요청받은 데이터로 Memo 객체 생성 -> Id 없음(MemoRequestDto는 id값이 없기 때문)
        Schedule schedule = new Schedule(dto.getUserName(), dto.getTitle(), dto.getContents(), dto.getPassword());

        return schedulerRepository.saveSchedule(schedule);
    }

    //전체 일정 조회
    @Override
    public List<SchedulerResponseDto> findAllSchedules(String userName, String updatedAt) {

        List<SchedulerResponseDto> schedulesList = schedulerRepository.findAllSchedules(userName, updatedAt);

        if (schedulesList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results are founded");
        }

        return schedulerRepository.findAllSchedules(userName, updatedAt);
    }

    //선택 일정 조회(단건)
    @Override
    public SchedulerResponseDto findScheduleById(Long id) {
        Schedule schedule = schedulerRepository.findScheduleByIdOrElseThrow(id);

        return new SchedulerResponseDto(schedule);
    }
}