package com.kodilla.library.dto;

import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.Title;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookSpecimenDto{
        private Long specimenId;
        private Title title;
        private String status;
        private boolean isAvailableForRent;
        private List<Rental> rentals = new ArrayList<>();
}
