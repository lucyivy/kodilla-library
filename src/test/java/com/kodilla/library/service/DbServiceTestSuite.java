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
    public void testAddSpecimen() {
        //Given
        Title title = new Title(null, "test", "test", 154, new ArrayList<BookSpecimen>());
        BookSpecimen specimen = new BookSpecimen(null, title, "new", true, new ArrayList<Rental>());
        Integer specimensBeforeSave = service.findSpecimens().size();
        //When
        service.addSpecimen(specimen);

        //Then
        Assert.assertEquals(specimensBeforeSave+1, service.findSpecimens().size());

        //Cleanup
        service.deleteSpecimen(specimen.getSpecimenId());
    }
}
