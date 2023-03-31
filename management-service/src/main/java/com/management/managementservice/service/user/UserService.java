package com.management.managementservice.service.user;

import com.management.managementservice.domain.User;
import com.management.managementservice.enums.ERole;
import com.management.managementservice.model.request.AuthRequest;
import com.management.managementservice.model.response.AuthResponse;
import com.management.managementservice.model.request.UserRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User createUser(UserRequestDto userRequestDto);
    User updateUser(int id,UserRequestDto userRequestDto);

    User getUser(int id);
    List<User> getUsers();
    User addRole(int userId, ERole eRole);
    User removeRole(int userId, ERole eRole);
    void deleteUser(int id);

    void  parseClaims(String token);

    AuthResponse login(AuthRequest authRequest);
}
