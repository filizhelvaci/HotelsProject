package com.flz.repository;

import com.flz.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUsersRepository extends JpaRepository<Users,Long> {

    Boolean existsByEmail(String email);

    Optional<Users> findByEmail(String email);

    Optional<Users> findOptionalByEmailAndPassword (String email, String password);

}
