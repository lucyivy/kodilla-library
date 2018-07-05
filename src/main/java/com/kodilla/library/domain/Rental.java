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

    @Transient
    private BookSpecimen specimen;
    @Transient
    private User user;

    @Column(name="Rented Date")
    private LocalDate rentedDate;

    @Column(name="Return Date")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "Specimen_Id")
    public BookSpecimen getSpecimen() {
        return specimen;
    }

    @ManyToOne
    @JoinColumn(name = "User_Id")
    public User getUser() {
        return user;
    }
}
