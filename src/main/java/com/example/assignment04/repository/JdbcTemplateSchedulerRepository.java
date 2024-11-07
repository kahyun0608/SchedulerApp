package com.example.assignment04.repository;

import com.example.assignment04.dto.SchedulerResponseDto;
import com.example.assignment04.dto.SchedulerResponseDtoForSaveSchedule;
import com.example.assignment04.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public SchedulerResponseDtoForSaveSchedule saveSchedule(Schedule schedule) {

        //INSERT Query 직접 작성하지 않아도 된다.
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingGeneratedKeyColumns("id").usingColumns("user_name", "title", "contents", "password");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_name", schedule.getUserName());
        parameters.put("title", schedule.getTitle());
        parameters.put("contents", schedule.getContents());
        parameters.put("password", schedule.getPassword());

        //저장 후 생성된 key값을 Number 타입으로 반환하는 메서드
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        List<Schedule> result = jdbcTemplate.query("select * from schedules where id = ?", scheduleRowMapperV2(), key);

        return new SchedulerResponseDtoForSaveSchedule(result.stream().findAny().get());

    }

    @Override
    public List<SchedulerResponseDto> findAllSchedules(String userName, String updatedAt) {

        //조건문 : userName이 입력되었을 경우, updatedAt이 입력되었을 경우, 모두 입력되었을 경우
        //If문을 service layer로 넘기고 repository layer에 메서드를 여러개 만드는 게 나을까?
        if (userName != null && updatedAt == null){
            return jdbcTemplate.query("select * from schedules where user_name = ? order by updated_at desc", scheduleRowMapper(), userName);
        } else if (userName == null && updatedAt != null){
            return jdbcTemplate.query("select * from schedules where substring(updated_at, 1, 10) = ? order by updated_at desc", scheduleRowMapper(), updatedAt);
        } else if (userName != null && updatedAt != null) {
            return jdbcTemplate.query("select * from schedules where user_name = ? and substring(updated_at, 1, 10) = ? order by updated_at desc", scheduleRowMapper(), userName, updatedAt);
        } else {
            return jdbcTemplate.query("select * from schedules order by updated_at desc", scheduleRowMapper());
        }

    }

    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> result = jdbcTemplate.query("select * from schedules where id = ?", scheduleRowMapperV2(), id);

        return result.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Does not exists id = " + id));
    }

    @Override
    public int updateSchedule(Long id, String userName, String title, String contents) {

         return jdbcTemplate.update("update schedules set user_name = ?, title = ?, contents = ? where id = ?", userName, title, contents, id);

    }

    //비밀번호 확인 메서드
    public boolean checkPassword(Long id, String password){
        String dbPassword = jdbcTemplate.query("select * from schedules where id = ?", scheduleRowMapperV3(), id).stream().findAny().get().getPassword();
        if (dbPassword.equals(password)){
            return true;
        } else {
            return false;
        }
    }

    private RowMapper<SchedulerResponseDto> scheduleRowMapper() {
        return new RowMapper<SchedulerResponseDto>() {
            @Override
            public SchedulerResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new SchedulerResponseDto(
                        rs.getLong("id"),
                        rs.getString("user_name"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("updated_at")
                );
            }
        };
    }

    private RowMapper<Schedule> scheduleRowMapperV2() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("user_name"),
                        rs.getString("title"),
                        rs.getString("contents"),
                        rs.getString("password"),
                        rs.getString("created_at"),
                        rs.getString("updated_at")
                );
            }
        };
    }

    public RowMapper<Schedule> scheduleRowMapperV3() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(rs.getString("password"));
            }
        };
    }
}
