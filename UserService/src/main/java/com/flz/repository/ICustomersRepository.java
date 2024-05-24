package com.flz.repository;

import com.flz.model.Customers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomersRepository extends JpaRepository<Customers,Long>{
}
