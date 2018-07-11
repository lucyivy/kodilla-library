package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.awt.print.Book;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="Rentals")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "Specimen_Id")
    private BookSpecimen specimen;

    @ManyToOne
    @JoinColumn(name = "User_Id")
    private User user;

    @Column(name="Rented_Date")
    private LocalDate rentedDate;

    @Column(name="Return_Date")
    private LocalDate returnDate;

    @Column(name="Date_Returned")
    private LocalDate dateReturned;

    public BookSpecimen getSpecimen() {
        return specimen;
    }

    public User getUser() {
        return user;
    }

}
