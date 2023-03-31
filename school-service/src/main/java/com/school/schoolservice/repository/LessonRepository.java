package com.school.schoolservice.repository;

import com.school.schoolservice.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson,Integer> {

    Lesson findByLessonName(String lessonName);

}
