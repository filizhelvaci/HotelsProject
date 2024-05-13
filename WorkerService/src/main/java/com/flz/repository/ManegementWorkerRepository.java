package com.flz.repository;

import com.flz.model.Employees;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ManegementWorkerRepository extends JpaRepository<Employees,Long> {

}
