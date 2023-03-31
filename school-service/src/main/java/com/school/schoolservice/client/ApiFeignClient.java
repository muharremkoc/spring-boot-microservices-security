package com.school.schoolservice.client;

import com.school.schoolservice.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "user-service", url = "http://localhost:6666/api/versions/1/auth")
public interface ApiFeignClient {


    @GetMapping(path = "/user/{id}")
    User getUser(@RequestHeader("Authorization")String token, @PathVariable("id") Integer id);


}