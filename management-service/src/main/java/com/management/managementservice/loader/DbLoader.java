package com.management.managementservice.loader;

import com.management.managementservice.domain.Role;
import com.management.managementservice.domain.User;
import com.management.managementservice.enums.ERole;
import com.management.managementservice.repository.RoleRepository;
import com.management.managementservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class DbLoader implements ApplicationRunner {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        if (roleRepository.count() == 0)
            Arrays.stream(ERole.values()).map(Role::new).forEach(role -> roleRepository.save(role));

        if (userRepository.count() == 0) {

            BCryptPasswordEncoder bCryptPasswordEncoder =  new BCryptPasswordEncoder();

            userRepository.save(new User("root","root@root.rot",bCryptPasswordEncoder.encode("root"),List.of(roleRepository.findByName(ERole.PRINCIPAL),roleRepository.findByName(ERole.TEACHER))));

        }


    }
}