package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import com.udacity.jdnd.course3.critter.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Schedule createSchedule(Schedule schedule, List<Long> empIds, List<Long> petIds) {
        schedule.setEmployees(employeeRepository.findAllById(empIds));
        schedule.setPets(petRepository.findAllById(petIds));
        return scheduleRepository.save(schedule);
    }

    public List<Schedule> findAllSchedules() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findAllByPetId(long id){
        Pet pet = petRepository.findById(id).orElseThrow
                (() -> new RuntimeException("Pet id: " + id + " does not exist"));
        return scheduleRepository.getAllByPet(pet);
    }

    public List<Schedule> findAllByEmployeeId(long id){
        Employee employee = employeeRepository.findById((id)).orElseThrow
                (() -> new RuntimeException("Employee id: " + id + " does not exist"));
        return scheduleRepository.getAllByEmployee(employee);
    }

    public List<Schedule> findAllByCustomerId(long id){
        Customer customer = customerRepository.findById(id).orElseThrow
                (() -> new RuntimeException("Customer id: " + id + " does not exist"));
        List<Pet> pets = customer.getPets();
        return scheduleRepository.getAllByPets(pets);
    }

}
