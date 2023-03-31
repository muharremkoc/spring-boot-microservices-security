package com.school.schoolservice.dto;

import com.school.schoolservice.domain.Lesson;
import com.school.schoolservice.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LessonResponseDto {

    Lesson lesson;

    Set<User> userList = new HashSet<>();


    public LessonResponseDto(Lesson lesson, Set<User> userList) {
        this.lesson = lesson;
        this.userList = userList;
    }


    public LessonResponseDto() {
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public Set<User> getUserList() {
        return userList;
    }

    public void setUserList(Set<User> userList) {
        this.userList = userList;
    }

    public void addUser(User user){
         this.userList.add(user);
        }
}
