package com.school.schoolservice.client;

import com.school.schoolservice.model.User;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


@Component
public class ApiFeignFactory implements ApiFeignClient {




    final ApiFeignClient apiFeignClient;

    public ApiFeignFactory(    @Qualifier("com.school.schoolservice.client.ApiFeignClient") ApiFeignClient apiFeignClient) {
        this.apiFeignClient = apiFeignClient;
    }

    @Override

    public User getUser(String token ,Integer id) {
        return apiFeignClient.getUser(token,id);
    }

}