package com.udacity.jdnd.course3.critter.controllers;

import com.udacity.jdnd.course3.critter.dtos.ScheduleDTO;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.Schedule;
import com.udacity.jdnd.course3.critter.entities.users.Employee;
import com.udacity.jdnd.course3.critter.services.ScheduleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        return convertScheduleToScheduleDTO(scheduleService.createSchedule(
                                                convertScheduleDTOToSchedule(scheduleDTO),
                                                scheduleDTO.getEmployeeIds(),
                                                scheduleDTO.getPetIds()));

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        return scheduleService.findAllSchedules()
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        return scheduleService.findAllByPetId(petId)
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        return scheduleService.findAllByEmployeeId(employeeId)
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        return scheduleService.findAllByCustomerId(customerId)
                .stream()
                .map(schedule -> convertScheduleToScheduleDTO(schedule))
                .collect(Collectors.toList());
    }

    // Convert Schedule Entity to ScheduleDTO
    public ScheduleDTO convertScheduleToScheduleDTO(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        List<Long> petIds = new ArrayList<>();
        List<Long> employeeIds = new ArrayList<>();
        BeanUtils.copyProperties(schedule, scheduleDTO);

        // Get Ids fromm schedule and fill the lists
        for (Pet pet : schedule.getPets()) {
            petIds.add(pet.getId());
        }

        for (Employee employee : schedule.getEmployees()) {
            employeeIds.add(employee.getId());
        }

        // Add the Id lists to the DTO
        scheduleDTO.setPetIds(petIds);
        scheduleDTO.setEmployeeIds(employeeIds);
        return scheduleDTO;
    }

    // Convert ScheduleDTO to Schedule Entity
    public Schedule convertScheduleDTOToSchedule(ScheduleDTO scheduleDTO) {
            Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleDTO, schedule);
        return schedule;
    }

}
