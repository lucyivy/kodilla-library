package com.kodilla.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name="Titles")
public class Title {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="Title")
    @NotNull
    private String title;

    @Column(name="Author")
    private String author;

    @Column(name="PublicationDate")
    private int publishedYear;

    @Transient
    private List<BookSpecimen> specimens = new ArrayList<>();

    @OneToMany(
            targetEntity = BookSpecimen.class,
            mappedBy = "title",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    public List<BookSpecimen> getSpecimens() {
        return specimens;
    }
}
