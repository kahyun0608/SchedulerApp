package com.example.assignment04.dto;

import com.example.assignment04.entity.Schedule;
import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@AllArgsConstructor
public class SchedulerResponseDto {

    private Long id;

    private String userName;

    private String title;

    private String contents;

    private String updated_at;


    public SchedulerResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.updated_at = schedule.getUpdated_at();

    }

}
