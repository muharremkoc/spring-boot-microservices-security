package com.school.schoolservice.service;

import com.school.schoolservice.domain.Lesson;
import com.school.schoolservice.domain.LessonWithUser;
import com.school.schoolservice.dto.LessonRequestDto;
import com.school.schoolservice.dto.LessonResponseDto;

import java.util.List;
import java.util.Set;

public interface LessonService {

    Lesson create(LessonRequestDto lessonRequestDto);

    LessonWithUser addUserInLesson(String token, int lessonId, int userId);

    Set<LessonResponseDto> getLessons(String token);
}
