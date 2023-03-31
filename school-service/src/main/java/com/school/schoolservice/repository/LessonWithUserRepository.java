package com.school.schoolservice.repository;

import com.school.schoolservice.domain.LessonWithUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonWithUserRepository extends JpaRepository<LessonWithUser,Integer> {

    LessonWithUser findByLessonId(int lessonId);
}
