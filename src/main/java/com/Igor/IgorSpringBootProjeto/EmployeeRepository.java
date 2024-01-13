package com.Igor.IgorSpringBootProjeto;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {
    List<Employee> findAll();

    Optional<Employee> findById(String id);
    
    List<Employee> findByName(String name);
}


