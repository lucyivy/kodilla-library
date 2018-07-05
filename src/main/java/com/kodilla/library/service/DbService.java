package com.kodilla.library.service;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.User;
import com.kodilla.library.repository.BookSpecimenRepository;
import com.kodilla.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookSpecimenRepository bookRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setJoiningDate(LocalDate.now());
        return userRepository.save(user);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public BookSpecimen addSpecimen(BookSpecimen specimen) {
        return bookRepository.save(specimen);
    }

    public Optional<BookSpecimen> findById(Long Id) {
        return bookRepository.findById(Id);
    }

    public List<BookSpecimen> findByTitle(String title) {

        return new ArrayList<>();
    }
}
