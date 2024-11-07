package com.example.assignment04.service;

import com.example.assignment04.dto.SchedulerRequestDto;
import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;
import com.example.assignment04.entity.Schedule;
import com.example.assignment04.repository.JdbcTemplateSchedulerRepository;
import com.example.assignment04.repository.SchedulerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SchedulerServiceImpl implements SchedulerService {

    //repository 사용위해 필드와 생성자 지정
    private final SchedulerRepository schedulerRepository;
    private final JdbcTemplateSchedulerRepository jdbcTemplateSchedulerRepository;

    public SchedulerServiceImpl(SchedulerRepository schedulerRepository, JdbcTemplateSchedulerRepository jdbcTemplateSchedulerRepository) {
        this.schedulerRepository = schedulerRepository;
        this.jdbcTemplateSchedulerRepository = jdbcTemplateSchedulerRepository;
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

    @Transactional
    @Override
    public SchedulerResponseDto updateSchedule(Long id, String userName, String title, String contents, String password) {

        Schedule schedule = schedulerRepository.findScheduleByIdOrElseThrow(id);

        //비밀번호 확인 -> 윗줄에서 받아온 schedule에 password가 이미 담겨있다
        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password.");
        }

        //입력 항목들이 null일 경우, 위에서 받아온 schedule의 값을 대입해 null로 업데이트 되는 것을 방지
        //입력 항목이 이미 입력된 값과 같은 경우 업데이트를 해도 변화가 없으니 상관X
        if (userName == null) {
            userName = schedule.getUserName();
        }

        if (title == null) {
            title = schedule.getTitle();
        }

        if (contents == null) {
            contents = schedule.getContents();
        }

        //수정된 사항이 없다면 입력값이 조건에 맞지 않는 것이므로 예외처리
        int updatedRow = schedulerRepository.updateSchedule(id, userName, title, contents);

        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This is a wrong request.");
        }

        //마지막으로 수정된 값들을 다시 조회
        Schedule updatedSchedule = schedulerRepository.findScheduleByIdOrElseThrow(id);

        return new SchedulerResponseDto(updatedSchedule);
    }

    //선택 일정 삭제
    @Override
    public void deleteSchedule(Long id, String password) {

        Schedule schedule = schedulerRepository.findScheduleByIdOrElseThrow(id);

        if (!schedule.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password.");
        }

        int deletedRow = schedulerRepository.deleteSchedule(id);

        //id랑 패스워드를 잘 입력했지만 내부적인 오류가 터져서 삭제되지 않았을 경우
        if (deletedRow == 0) {
            throw new ResponseStatusException((HttpStatus.INTERNAL_SERVER_ERROR), "");
        }
    }

}
