package com.udacity.jdnd.course3.critter.entities.users;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.udacity.jdnd.course3.critter.entities.Pet;
import com.udacity.jdnd.course3.critter.enums.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Customer extends User{

    @Enumerated(EnumType.STRING)
    @JsonIgnoreProperties
    private UserType userType;

    @Nationalized
    private String name;

    private String phoneNumber;

    @Column(length = 1000)
    private String notes;

    @NotNull
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new java.util.ArrayList<>();

    public void addPet(Pet pet){
        pets.add(pet);
    }
}
