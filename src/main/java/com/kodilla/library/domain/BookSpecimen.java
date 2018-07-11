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

    @ManyToOne
    @JoinColumn(name = "Title_Id")
    private Title title;

    @Column(name="Status")
    private String status;

    @Column(name="AvailabilityForRent")
    private boolean isAvailableForRent;

    @Transient
    private List<Rental> rentals = new ArrayList<>();

    private final static String NEW = "new";
    private final static String LOST = "lost";
    private final static String DESTROYED = "destroyed";
    private final static String USED = "used";


    @OneToMany(
            targetEntity = Rental.class,
            mappedBy = "specimen",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    public List<Rental> getRentals() {
        return rentals;
    }

}
