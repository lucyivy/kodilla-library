package com.kodilla.library.dto;

import com.kodilla.library.domain.Rental;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate joiningDate;
    private List<Rental> rentals;

    public UserDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserDto(String firstName, String lastName, LocalDate joiningDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.joiningDate = joiningDate;
    }
}
