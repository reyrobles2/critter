package com.udacity.jdnd.course3.critter.entities.users;

import com.udacity.jdnd.course3.critter.enums.UserType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Nationalized;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @Enumerated(EnumType.STRING)
    private UserType userType;
}
