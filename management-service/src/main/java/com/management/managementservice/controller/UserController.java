package com.management.managementservice.controller;

import com.management.managementservice.domain.User;
import com.management.managementservice.enums.ERole;
import com.management.managementservice.model.request.AuthRequest;
import com.management.managementservice.model.response.AuthResponse;
import com.management.managementservice.model.request.UserRequestDto;
import com.management.managementservice.service.user.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/versions/1/auth")
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(path = "/user")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL')")
    public User createUser(@RequestBody UserRequestDto userRequestDto){
        return userService.createUser(userRequestDto);
    }

    @PutMapping(path = "/user/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL')")
    public User updateUser(@PathVariable("id") int id ,@RequestBody UserRequestDto userRequestDto){
        return userService.updateUser(id, userRequestDto);
    }


    @PutMapping(path = "/user/{id}/role/add")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL')")
    public User addRoleForUser(@PathVariable("id") int id ,@RequestParam ERole eRole){
        return userService.addRole(id,eRole);
    }

    @PutMapping(path = "/user/{id}/role/remove")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL')")
    public User removeRoleForUser(@PathVariable("id") int id , @RequestParam ERole eRole){
        return userService.removeRole(id, eRole);
    }

    @GetMapping(path = "/user")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL','TEACHER')")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping(path = "/user/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL','TEACHER')")
    public User getUser(@PathVariable("id") int id){
        return userService.getUser(id);
    }

    @DeleteMapping(path = "/user/{id}")
    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAnyAuthority('PRINCIPAL')")
    public void deleteUser(@PathVariable("id") int id){
         userService.deleteUser(id);
    }

    @PostMapping(path = "/login")
    public AuthResponse login(@RequestBody AuthRequest authRequest){
        return userService.login(authRequest);
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        userService.parseClaims(token);
        return "Token is valid";
    }


}