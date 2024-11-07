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

    @Override
    public SchedulerResponseDtoForSaveSchedule saveSchedule(SchedulerRequestDto dto) {
        //요청받은 데이터로 Memo 객체 생성 -> Id 없음(MemoRequestDto는 id값이 없기 때문)
        Schedule schedule = new Schedule(dto.getUserName(), dto.getTitle(), dto.getContents(), dto.getPassword());

        // DB저장 ->레파지토리의 영역이므로 넘김
        //controller로 전달될 때 dto 형태로 전달
        return schedulerRepository.saveSchedule(schedule);
    }

    @Override
    public List<SchedulerResponseDto> findAllSchedules(String userName, String updatedAt) {

        List<SchedulerResponseDto> schedulesList = schedulerRepository.findAllSchedules(userName, updatedAt);

        if (schedulesList.size() == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No results are founded");
        }

        return schedulerRepository.findAllSchedules(userName, updatedAt);
    }
}
