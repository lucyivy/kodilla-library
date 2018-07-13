package com.kodilla.library.repository;

import com.kodilla.library.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {
    @Override
    List<User> findAll();

    @Override
    User save(User user);

    @Override
    void deleteById(Long Id);
}
