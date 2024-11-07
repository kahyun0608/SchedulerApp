package com.example.assignment04.entity;


import lombok.*;
import org.springframework.scheduling.annotation.Schedules;

import java.sql.Timestamp;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Schedule {

    //필드
    private Long id;
    private String userName;
    private String title;
    private String contents;
    private String password;

    private String createdAt;
    private String updatedAt;

    //생성자
    public Schedule(String userName, String title, String contents , String password){
        this.userName = userName;
        this.title = title;
        this.contents = contents;
        this.password = password;
    }

    public Schedule(String password){
        this.password = password;
    }

}
