package com.school.schoolservice.domain;

import com.school.schoolservice.model.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "lesson_users")
public class LessonWithUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int lessonId;

    private int  userId;

    public LessonWithUser() {
    }

    public LessonWithUser(int id, int lessonId, int userId) {
        this.id = id;
        this.lessonId = lessonId;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLessonId() {
        return lessonId;
    }

    public void setLessonId(int lessonId) {
        this.lessonId = lessonId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
