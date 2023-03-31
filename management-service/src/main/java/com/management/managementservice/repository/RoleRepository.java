package com.management.managementservice.repository;


import com.management.managementservice.domain.Role;
import com.management.managementservice.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(ERole eRole);

}