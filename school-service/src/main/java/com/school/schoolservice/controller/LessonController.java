package com.school.schoolservice.controller;

import com.school.schoolservice.domain.Lesson;
import com.school.schoolservice.domain.LessonWithUser;
import com.school.schoolservice.dto.LessonRequestDto;
import com.school.schoolservice.dto.LessonResponseDto;
import com.school.schoolservice.service.LessonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/versions/1/lesson")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("")
    public Lesson createLesson(@RequestBody LessonRequestDto lessonRequestDto){
      return lessonService.create(lessonRequestDto);
    }

    @PutMapping(value = "/{id}")
    public LessonWithUser addUserInLesson(@RequestParam String token, @PathVariable("id") int lessonId, @RequestParam int userId){
        return lessonService.addUserInLesson("Bearer "+token,lessonId, userId);
    }

    @GetMapping("")
    public Set<LessonResponseDto> getLessons(@RequestParam String token){
        return lessonService.getLessons("Bearer "+token);
    }
}
