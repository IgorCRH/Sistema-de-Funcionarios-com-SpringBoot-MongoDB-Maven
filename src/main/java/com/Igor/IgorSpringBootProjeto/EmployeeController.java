package com.Igor.IgorSpringBootProjeto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "api/employees")
@CrossOrigin(origins = "http://localhost:8080/index.html")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(produces = "application/json")
    @ResponseBody
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public String getEmployeeById(@RequestParam String id, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee.orElse(null));
        return "employee-details";
    }
    
    @PostMapping("/search")
    public List<Employee> searchEmployeesByName(@RequestBody Map<String, String> payload) {
        String searchName = payload.get("name");
        return employeeService.searchEmployeesByName(searchName);
    }

    @PostMapping
    public String addEmployee(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "salary", required = false) Double salary,
            @RequestParam(value = "position", required = false) String position,
            @RequestParam(value = "hoursWorked", required = false) Integer hoursWorked) {

        String newName = (name != null) ? name : "";
        Double newSalary = (salary != null) ? salary : 0.0;
        String newPosition = (position != null) ? position : "";
        Integer newHoursWorked = (hoursWorked != null) ? hoursWorked : 0;

        Employee newEmployee = new Employee(newName, newSalary, newPosition, newHoursWorked);
        employeeService.addEmployee(newEmployee);
        return "redirect:/api/employees";
    }


    @PostMapping("/update-salary")
    public String updateSalary(
            @RequestParam String id,
            @RequestParam double newSalary) {
        employeeService.updateSalary(id, newSalary);
        return "redirect:/api/employees"; 
    }

    @DeleteMapping("/{id}/delete")
    public String deleteEmployee(@PathVariable String id) {
        employeeService.deleteEmployee(id);
        return "redirect:/api/employees";
    }

}
