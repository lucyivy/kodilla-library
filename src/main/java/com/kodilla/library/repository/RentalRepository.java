package com.kodilla.library.repository;

import com.kodilla.library.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RentalRepository extends CrudRepository<Rental, Long> {

    @Override
    Rental save(Rental rental);

    @Override
    List<Rental> findAll();
}
