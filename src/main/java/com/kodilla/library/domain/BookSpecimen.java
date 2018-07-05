package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="Specimens")
public class BookSpecimen {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long specimenId;

    @Transient
    private Title title;

    @Column(name="Status")
    private String status;

    @Column(name="Availability For Rent")
    private boolean isAvailableForRent;

    @Transient
    private List<Rental> rentals;

    private final static String NEW = "new";
    private final static String LOST = "lost";
    private final static String DESTROYED = "destroyed";
    private final static String USED = "used";


    public BookSpecimen(Long specimenId, Title title) {
        this.specimenId = specimenId;
        this.title = title;
        this.status = NEW;
        isAvailableForRent = true;
        this.rentals = new ArrayList<>();
    }

    public BookSpecimen(Long specimenId, Title title, String status) {
        this.specimenId = specimenId;
        this.title = title;
        this.status = status;
        this.rentals = new ArrayList<>();
        if(status == NEW || status == USED) {
            this.isAvailableForRent = true;
        } else {
            this.isAvailableForRent = false;
        }
    }

    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "specimen",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    public List<Rental> getRentals() {
        return rentals;
    }

    @ManyToOne
    @JoinColumn(name = "Title_Id")
    public Title getTitle() {
        return title;
    }
}
