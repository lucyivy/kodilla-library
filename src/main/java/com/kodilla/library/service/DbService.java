package com.kodilla.library.service;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.Title;
import com.kodilla.library.domain.User;
import com.kodilla.library.dto.BookSpecimenDto;
import com.kodilla.library.repository.BookSpecimenRepository;
import com.kodilla.library.repository.RentalRepository;
import com.kodilla.library.repository.TitleRepository;
import com.kodilla.library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;


@Service
public class DbService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookSpecimenRepository bookRepository;

    @Autowired
    private TitleRepository titleRepository;

    @Autowired
    private RentalRepository rentalRepository;

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        user.setJoiningDate(LocalDate.now());
        return userRepository.save(user);
    }

    public void deleteUser(Long Id) {
        userRepository.deleteById(Id);
    }

    public BookSpecimen addSpecimen(BookSpecimen specimen) {
        specimen.getTitle().getSpecimens().add(specimen);
        Title savedTitle = saveTitle(specimen.getTitle());
        specimen.setTitle(savedTitle);
        BookSpecimen result = bookRepository.save(specimen);

        return result;
    }

    public void deleteSpecimen(Long Id) {bookRepository.deleteById(Id);}

    public Optional<BookSpecimen> findSpecimenById(Long Id) {
        return bookRepository.findById(Id);
    }

    public List<BookSpecimen> findSpecimens() {
        return bookRepository.findAll();
    }

    public List<BookSpecimen> findAvailableSpecimenByTitle(String title) {
        List<Title> matchingTitles = new ArrayList<>();
        for(Title ttl : titleRepository.findAll()) {
            System.out.println(ttl.getTitle());
            if(ttl.getTitle() != null && ttl.getTitle().toLowerCase().contains(title.toLowerCase())) {
                matchingTitles.add(ttl);
            }
        }

        List<BookSpecimen> specimens = bookRepository.findAll();
        List<BookSpecimen> matchingSpecimens = new ArrayList<>();

        for(Title ttl : matchingTitles) {
            for(BookSpecimen specimen : specimens) {
                if(specimen.getTitle().equals(ttl) && specimen.isAvailableForRent()) {
                    matchingSpecimens.add(specimen);
                }
            }
        }

        return matchingSpecimens;
    }

    public Title saveTitle(Title argument) {
        Title duplicated = null;
        for(Title title : titleRepository.findAll()) {
            if(title.getTitle() != null && title.getAuthor() != null && title.getPublishedYear() != 0
                    && title.getTitle().equalsIgnoreCase(argument.getTitle())
                    && title.getAuthor().equalsIgnoreCase(argument.getAuthor())
                    && title.getPublishedYear() == argument.getPublishedYear()) {
                duplicated = title;
                break;
            }
        }
        if(duplicated == null) {
            return titleRepository.save(argument);
        } else {
            //duplicated.setSpecimens(argument.getSpecimens());
            return titleRepository.save(duplicated);
        }
    }

    public void deleteTitle(Long Id) {
        titleRepository.deleteById(Id);
    }

    public List<Title> findAllTitles() {
        return titleRepository.findAll();
    }

    public String rentBook(Long specimenId, Long userId) {
        User user = null;
        BookSpecimen specimen = findSpecimenById(specimenId).get();
        for(User us : listUsers()) {
            if(us.getId() == userId) {
                user = us;
            }
        }

        if(specimen == null || user == null) {
            return "Your rent was unsuccessful. Please check whether requested specimen is available for rent.";
        }
        Rental rental = new Rental();
        user.getRentals().add(rental);
        specimen.getRentals().add(rental);
        specimen.setAvailableForRent(false);
        rental.setUser(user);
        rental.setSpecimen(specimen);
        rental.setRentedDate(LocalDate.now());
        rental.setReturnDate(LocalDate.now().plusDays(14));
        Rental result = rentalRepository.save(rental);
        if(result != null) {
            return "Your rent was successful. Please return the book by " + result.getReturnDate() + ". Your rent Id is: " +
                    result.getId();
        } else {
            return "Your rent was unsuccessful. Please check whether requested specimen is available for rent.";
        }
    }

    public String returnBook(Long rentId) {
        Rental currentRent = null;
        for(Rental rental : rentalRepository.findAll()) {
            if(rental.getId() ==  rentId && rental.getDateReturned() == null) {
                currentRent = rental;
                break;
            }
        }
        if(currentRent == null ) {
            return "Invalid rent Id, please try again";
        }
        currentRent.setDateReturned(LocalDate.now());
        Rental result = rentalRepository.save(currentRent);
        BookSpecimen specimen = result.getSpecimen();
        specimen.setAvailableForRent(true);
        bookRepository.save(specimen);
        if(result != null && !result.getDateReturned().isAfter(result.getReturnDate())) {
            return "Return successful. Thank you for using our services!";
        } else if (result != null && result.getDateReturned().isAfter(result.getReturnDate())) {
            return "Return successful. However, as you are " + DAYS.between(result.getReturnDate(), result.getDateReturned())
                    + " days late after your due return date, you will be charged penalty fee";
            } else {
            return "Return unsuccessful. No relevant rent found in database. Please try again.";
        }
    }
}
