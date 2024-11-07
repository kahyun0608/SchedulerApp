package com.example.assignment04.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    //일정 단건 업데이트 (할일, 작성자명) 메서드


}
