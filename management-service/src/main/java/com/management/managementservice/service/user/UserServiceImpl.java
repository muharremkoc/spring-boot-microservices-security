package com.management.managementservice.service.user;

import com.management.managementservice.domain.Role;
import com.management.managementservice.domain.User;
import com.management.managementservice.enums.ERole;
import com.management.managementservice.model.request.AuthRequest;
import com.management.managementservice.model.response.AuthResponse;
import com.management.managementservice.model.request.UserRequestDto;
import com.management.managementservice.repository.RoleRepository;
import com.management.managementservice.repository.UserRepository;
import com.management.managementservice.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }



    @Override
    public AuthResponse login(AuthRequest authRequest) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        User authenticatedUser = userRepository.findByUsername(authRequest.getUsername());
        if (authenticatedUser ==null){
            throw  new BadCredentialsException("Geçersiz kullanıcı adı ya da şifre");
        }

        boolean isPasswordMatch = passwordEncoder.matches(authRequest.getPassword(),authenticatedUser.getPassword());
        if (!isPasswordMatch){
            throw new BadCredentialsException("Şifreler eşleşmedi");
        }

        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(),authRequest.getPassword(),new ArrayList<>()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }catch (Exception e){
            throw new IllegalStateException("Authentication failed " + e);
        }

        String accessToken = jwtService.generateToken(authenticatedUser);
        AuthResponse response = new AuthResponse(authenticatedUser.getEmail(),accessToken);

        return response;

    }

    @Override
    public User createUser(UserRequestDto userRequestDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();

        User newUser = new User();
        newUser.setEmail(userRequestDto.getEmail());
        newUser.setUsername(userRequestDto.getUsername());
        newUser.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));

        return userRepository.save(newUser);
    }

    @Override
    public User updateUser(int id, UserRequestDto userRequestDto) {
        return null;
    }

    @Override
    public User getUser(int id) {
        return userRepository.findById(id).orElseThrow(()->new BadCredentialsException("Kullanıcı Mevcut Değil"));
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addRole(int userId, ERole eRole) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(eRole);

        user.getRoles().add(role);
        user.getRoles().stream().forEach(r -> r.setUsers(Arrays.asList(user)));

        return userRepository.save(user);
    }

    @Override
    public User removeRole(int userId, ERole eRole) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(eRole);

        if (role != null) user.getRoles().remove(role);
        user.getRoles().stream().forEach(r -> r.setUsers(Arrays.asList(user)));

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);

    }

    @Override
    public void parseClaims(String token) {
        jwtService.parseClaims(token);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) throw  new BadCredentialsException("Geçersiz Kullanıcı adı ya da şifre");

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),user.getPassword(),new ArrayList<>());

    }
}
