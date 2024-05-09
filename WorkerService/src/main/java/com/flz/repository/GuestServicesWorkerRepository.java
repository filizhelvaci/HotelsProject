package com.flz.repository;

import com.flz.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestServicesWorkerRepository extends JpaRepository<Users,Long> {
}
