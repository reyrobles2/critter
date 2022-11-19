package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.DayOfWeek;
import java.util.List;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee findById(long id) {
        return employeeRepository.findById(id).orElseThrow
                (() -> new RuntimeException("Employee id: " + id + " doest not exist"));
    }

    public List<Employee> findAvailableEmployees(DayOfWeek day) {
        return employeeRepository.getEmployeesByDaysAvailable(day);
    }
}
