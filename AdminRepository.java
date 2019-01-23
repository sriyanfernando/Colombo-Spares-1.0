package com.javasampleapproach.mysql.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import com.javasampleapproach.mysql.model.Customer;

public interface AdminRepository extends CrudRepository<Customer , Long> {
    @Modifying
    @Transactional
    void deleteByEmail(String email);

}
