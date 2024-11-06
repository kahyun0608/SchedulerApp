package com.example.assignment04.dto;

import com.example.assignment04.entity.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
public class SchedulerResponseDto {

    private Long id;
    private String userName;
    private String title;
    private String contents;
    private Timestamp updated_at;

    public SchedulerResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.updated_at = schedule.getUpdated_at();
    }

}
