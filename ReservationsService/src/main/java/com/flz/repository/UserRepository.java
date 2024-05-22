package com.flz.repository;

import com.flz.model.User;
import jakarta.persistence.Inheritance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
