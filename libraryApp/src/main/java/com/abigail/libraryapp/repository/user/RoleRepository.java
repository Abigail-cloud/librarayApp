package com.abigail.libraryapp.repository.user;

import com.abigail.libraryapp.entity.user.OpRole;
import com.abigail.libraryapp.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role>findByName(OpRole name);
}
