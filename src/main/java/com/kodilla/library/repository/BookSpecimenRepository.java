package com.kodilla.library.repository;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Title;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookSpecimenRepository extends CrudRepository<BookSpecimen, Long> {

    @Override
    List<BookSpecimen> findAll();

    @Override
    Optional<BookSpecimen> findById(Long Id);

    List<BookSpecimen> findByTitle(Title title);

    @Override
    BookSpecimen save(BookSpecimen specimen);

    @Override
    void deleteById(Long Id);






}

