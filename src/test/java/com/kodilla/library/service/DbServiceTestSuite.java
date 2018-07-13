package com.kodilla.library.service;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.Title;
import com.kodilla.library.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DbServiceTestSuite {

    @Autowired
    private DbService service;

    @Test
    public void testSaveAndListUsers() {
        //Given
        User user = new User();
        service.saveUser(user);

        //When
        List<User> userList = service.listUsers();

        //Then
        Assert.assertNotEquals(0, userList.size());

        //Cleanup
        service.deleteUser(user.getId());
    }

    @Test
    public void testDeleteUser() {
        //Given
        User user = new User();
        service.saveUser(user);

        //When
        service.deleteUser(user.getId());

        //Then
        for(User us : service.listUsers()) {
            Assert.assertNotEquals(user.getId(), us.getId());
        }
    }


    @Test
    public void testFindSpecimens() {
        //Given
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        BookSpecimen specimen = new BookSpecimen(null, title, "new", true, new ArrayList<Rental>());

        //When
        service.addSpecimen(specimen);
        List<BookSpecimen> specimenList = service.findSpecimens();


        //Then
        Assert.assertNotEquals(0, specimenList.size());
        Assert.assertEquals(specimenList.get(specimenList.size()-1).getStatus(), specimen.getStatus());

        //Cleanup
        service.deleteSpecimen(specimen.getSpecimenId());
    }

    @Test
    public void testAddAndDeleteSpecimen() {
        //Given
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        BookSpecimen specimen = new BookSpecimen(null, title, "new", true, new ArrayList<Rental>());
        int specimensBeforeSave = service.findSpecimens().size();
        //When
        service.addSpecimen(specimen);

        //Then
        Assert.assertEquals(specimensBeforeSave+1, service.findSpecimens().size());

        //Cleanup
        service.deleteSpecimen(specimen.getSpecimenId());
        Assert.assertEquals(specimensBeforeSave, service.findSpecimens().size());
    }

    @Test
    public void testFindSpecimenById() {
        //Given
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        BookSpecimen specimen = new BookSpecimen(null, title, "new", true, new ArrayList<Rental>());
        //When
        service.addSpecimen(specimen);
        BookSpecimen result = service.findSpecimenById(service.findSpecimens().get(0).getSpecimenId()).get();

        //Then
        Assert.assertEquals(result.getStatus(), specimen.getStatus());
        Assert.assertEquals(result.isAvailableForRent(), specimen.isAvailableForRent());
        Assert.assertEquals(result.getTitle().getTitle(), specimen.getTitle().getTitle());

        //Cleanup
        service.deleteSpecimen(result.getSpecimenId());
    }

    @Test
    public void testFindAvailableSpecimenByTitle() {
        //Given
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        BookSpecimen specimen = new BookSpecimen(null, title, "new", true, new ArrayList<Rental>());
        //When
        service.addSpecimen(specimen);
        List<BookSpecimen> specimenList = service.findAvailableSpecimenByTitle("test");
        List<BookSpecimen> specimenListNotMatching = service.findAvailableSpecimenByTitle("dlkfajfaldfkwldfslj");

        //Then
        Assert.assertNotEquals(0, specimenList.size());
        Assert.assertEquals(0, specimenListNotMatching.size());

        //Cleanup
        service.deleteSpecimen(specimenList.get(specimenList.size()-1).getSpecimenId());
    }

    @Test
    public void testSaveFindAndDeleteTitles() {
        //Given
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        int sizeBeforeSave = service.findAllTitles().size();
        //When
        Title savedTitle = service.saveTitle(title);
        List<Title> titles = service.findAllTitles();

        //Then
        Assert.assertEquals(savedTitle.getTitle(), title.getTitle());
        Assert.assertEquals(savedTitle.getAuthor(), title.getAuthor());
        Assert.assertEquals(savedTitle.getPublishedYear(), title.getPublishedYear());
        Assert.assertNotNull(savedTitle.getId());
        Assert.assertEquals(sizeBeforeSave+1, service.findAllTitles().size());

        //Cleanup
        service.deleteTitle(savedTitle.getId());
        Assert.assertEquals(sizeBeforeSave, service.findAllTitles().size());
    }

    @Test
    public void testRentAndReturnBooks() {
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        BookSpecimen specimen = new BookSpecimen(null, title, "new", true, new ArrayList<Rental>());
        BookSpecimen specimenUnavailable = new BookSpecimen(null, title, "new", false, new ArrayList<Rental>());

        BookSpecimen savedSpecimen = service.addSpecimen(specimen);
        BookSpecimen savedUnavailableSpecimen = service.addSpecimen(specimenUnavailable);
        User user = new User();
        User savedUser = service.saveUser(user);

        //When
        String rentResult = service.rentBook(savedSpecimen.getSpecimenId(), savedUser.getId());
        String rentResultNegative = service.rentBook(savedUnavailableSpecimen.getSpecimenId(), savedUser.getId());

        //Then
        Assert.assertTrue(rentResult.contains("was successful"));
        Assert.assertTrue(rentResultNegative.contains("was unsuccessful"));

        //When return & Cleanup
        Long rentId = Long.parseLong(rentResult.substring(rentResult.length()-2));
        String returnResult = service.returnBook(rentId);

        //Then
        Assert.assertTrue(returnResult.contains("successful"));
    }
}
