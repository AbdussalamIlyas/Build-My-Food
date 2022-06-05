package de.htwberlin.webtech.authentication.repository;

import de.htwberlin.webtech.authentication.models.ERole;
import de.htwberlin.webtech.authentication.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
