package com.example.assignment04.repository;

import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcTemplateSchedulerRepository implements SchedulerRepository {

    //필드
    private final JdbcTemplate jdbcTemplate;
    //생성자
    public JdbcTemplateSchedulerRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    //일정 생성
    @Override
    public SchedulerResponseDto saveSchedule(Schedule schedule) {

        //INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_name", schedule.getUserName());
        parameters.put("title", schedule.getTitle());
        parameters.put("`contents`", schedule.getContents());
        parameters.put("password", schedule.getPassword());

        //저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new SchedulerResponseDto(key.longValue(), schedule.getUserName(), schedule.getTitle(), schedule.getContents(),schedule.getCreated_at());
    }
}
