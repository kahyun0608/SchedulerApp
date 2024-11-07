package com.example.assignment04.dto;

import com.example.assignment04.entity.Schedule;
import lombok.*;

//saveSchedule 을 실행할 시 응답으로 created_at만을 호출하기 위한 ResponseDto (updqted_at X)

@Setter
@Getter
@AllArgsConstructor
public class SchedulerResponseDtoForSaveSchedule {

    private Long id;

    private String userName;

    private String title;

    private String contents;

    private String createdAt;


    public SchedulerResponseDtoForSaveSchedule(Schedule schedule) {
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.title = schedule.getTitle();
        this.contents = schedule.getContents();
        this.createdAt = schedule.getCreatedAt();
    }

}
