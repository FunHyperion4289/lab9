package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Customer {
    private int id;
    private String surname;
    private String name;
    private String middleName;
    private LocalDate birthday;
    private String address;
    private long CardNumber;
    private double CardBalance;


}
