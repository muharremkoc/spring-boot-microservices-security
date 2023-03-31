package com.school.schoolservice.service;

import com.school.schoolservice.client.ApiFeignClient;
import com.school.schoolservice.domain.Lesson;
import com.school.schoolservice.domain.LessonWithUser;
import com.school.schoolservice.dto.LessonRequestDto;
import com.school.schoolservice.dto.LessonResponseDto;
import com.school.schoolservice.model.User;
import com.school.schoolservice.repository.LessonRepository;
import com.school.schoolservice.repository.LessonWithUserRepository;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;

    private final LessonWithUserRepository lessonWithUserRepository;

    Set<LessonResponseDto> lessonResponseDtos=new HashSet<>();

    Set<User> users = new HashSet<>();


    private final ApiFeignClient apiFeignClient;

    public LessonServiceImpl(LessonRepository lessonRepository, LessonWithUserRepository lessonWithUserRepository, @Qualifier("apiFeignFactory") ApiFeignClient apiFeignClient) {
        this.lessonRepository = lessonRepository;
        this.lessonWithUserRepository = lessonWithUserRepository;
        this.apiFeignClient = apiFeignClient;
    }

    @Override
    public Lesson create(LessonRequestDto lessonRequestDto) {
        Lesson lesson = new Lesson();
        lesson.setLessonName(lessonRequestDto.getLessonName());

        return lessonRepository.save(lesson);
    }

    @Override
    public LessonWithUser addUserInLesson(String token, int lessonId, int userId) {

        Lesson existLesson = lessonRepository.findById(lessonId).orElseThrow(()-> new BadRequestException("Geçersiz Sınıf değeri"));

        User  user = apiFeignClient.getUser(token,userId);
        if (user ==null) throw new BadRequestException("Geçersiz kullanıcı girişi");
        LessonWithUser lessonWithUser = new LessonWithUser();
        lessonWithUser.setLessonId(existLesson.getId());
        lessonWithUser.setUserId(user.getId());
        return lessonWithUserRepository.save(lessonWithUser);
    }

    @Override
    public Set<LessonResponseDto> getLessons(String token) {



        lessonWithUserRepository.findAll().forEach(lessonWithUser -> {
            LessonResponseDto lessonResponseDto = new LessonResponseDto();

            Lesson lesson = lessonRepository.findById(lessonWithUser.getLessonId()).orElseThrow(()->new BadRequestException("Geçersiz Ders değeri"));
            User user = apiFeignClient.getUser(token,lessonWithUser.getUserId());
            lessonResponseDto.setLesson(lesson);
            lessonResponseDto.addUser(user);
            lessonResponseDtos.add(lessonResponseDto);

        });



        return lessonResponseDtos;
    }
}
