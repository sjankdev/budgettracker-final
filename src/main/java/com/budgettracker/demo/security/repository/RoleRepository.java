package com.budgettracker.demo.security.repository;

import com.budgettracker.demo.security.models.ERole;
import com.budgettracker.demo.security.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}
