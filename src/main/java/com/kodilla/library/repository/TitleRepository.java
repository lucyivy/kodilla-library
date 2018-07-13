package com.kodilla.library.repository;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TitleRepository extends CrudRepository<Title, Long> {

    @Override
    List<Title> findAll();

    @Override
    Title save(Title title);

    @Override
    void deleteById(Long Id);
}
