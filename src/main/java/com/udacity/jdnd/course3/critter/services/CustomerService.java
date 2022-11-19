package com.udacity.jdnd.course3.critter.services;

import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.entities.users.Customer;
import com.udacity.jdnd.course3.critter.repositories.CustomerRepository;
import com.udacity.jdnd.course3.critter.repositories.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PetRepository petRepository;

    public Customer saveCustomer(Customer customer, List<Long> ids) {
        List<Pet> pets = new ArrayList<>();
        // when saving a customer, add potential pet ids
        if (ids != null && !ids.isEmpty()) {
            for (Long id : ids) {
                pets.add(petRepository.findById(id).orElseThrow
                            (() -> new RuntimeException("Customer id: " + id + " could not be saved")));
            }
        }
        customer.setPets(pets);
        return customerRepository.save(customer);
    }

    public Customer findById(long id) {
        return customerRepository.findById(id).orElseThrow
                (() -> new RuntimeException("Customer id: " + id + " does not exist"));
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer findByPetId(long id) {
        Pet pet = petRepository.findById(id).orElseThrow
                (() -> new RuntimeException("Pet id: " + id + " does not exist"));
        return pet.getCustomer();
    }

}
