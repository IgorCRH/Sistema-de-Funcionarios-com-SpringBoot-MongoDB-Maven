package com.Igor.IgorSpringBootProjeto;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Funcionarios")
public class Employee {
    @Id
    private String id;
    private String name;
    private double salary;
    private String position;
    private int hoursWorked;

    public Employee() {
    }

    public Employee(String name, double salary, String position, int hoursWorked) {
        this.name = name;
        this.salary = salary;
        this.position = position;
        this.hoursWorked = hoursWorked;
    }

    // Getters e setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }

    // Outros métodos, se necessário
}
