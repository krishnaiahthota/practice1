package com.krish.practice.reposiroty;

import com.krish.practice.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);

    List<Role> findAllByName(String roleName);
}
