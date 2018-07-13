package com.kodilla.library.repository;

import com.kodilla.library.domain.BookSpecimen;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface BookSpecimenRepository extends CrudRepository<BookSpecimen, Long> {

    @Override
    List<BookSpecimen> findAll();

    @Override
    Optional<BookSpecimen> findById(Long Id);

    @Override
    BookSpecimen save(BookSpecimen specimen);

    @Override
    void deleteById(Long Id);

}

