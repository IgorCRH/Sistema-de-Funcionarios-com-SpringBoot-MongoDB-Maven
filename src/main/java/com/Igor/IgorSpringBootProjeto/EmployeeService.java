package com.Igor.IgorSpringBootProjeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Optional<Employee> getEmployeeById(String id) {
        return employeeRepository.findById(id);
    }
    
    public List<Employee> searchEmployeesByName(String name) {
        return employeeRepository.findByName(name);
    }

    public Employee addEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public void deleteEmployee(String id) {
        employeeRepository.deleteById(id);
    }

    public Employee updateSalary(String id, double newSalary) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            employee.setSalary(newSalary);
            return employeeRepository.save(employee);
        } else {
            throw new IllegalArgumentException("Employee not found with id: " + id);
        }
    }
}

