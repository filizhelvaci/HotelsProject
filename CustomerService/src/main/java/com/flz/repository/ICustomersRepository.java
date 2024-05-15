package com.flz.repository;

import com.flz.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICustomersRepository extends JpaRepository<Customer,Long>{
}
