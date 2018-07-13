package com.kodilla.library.mapper;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.Title;
import com.kodilla.library.domain.User;
import com.kodilla.library.dto.BookSpecimenDto;
import com.kodilla.library.dto.UserDto;
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
public class LibraryMapperTestSuite {

    @Autowired
    private LibraryMapper mapper;

    @Test
    public void testMapUserToUserDto() {
        //Given
        User user = new User(5L, "Johnny", "Swaczyna", LocalDate.now(), new ArrayList<Rental>());

        //When
        UserDto userDto = mapper.mapUserToUserDto(user);

        //Then
        Assert.assertEquals(user.getId(), userDto.getId());
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getJoiningDate(), userDto.getJoiningDate());
        Assert.assertEquals(user.getRentals(), userDto.getRentals());
    }

    @Test
    public void testMapUserDtoToUser() {
        //Given
        UserDto userDto = new UserDto(5L, "Johnny", "Swaczyna", LocalDate.now(), new ArrayList<Rental>());

        //When
        User user = mapper.mapUserDtoToUser(userDto);

        //Then
        Assert.assertEquals(user.getId(), userDto.getId());
        Assert.assertEquals(user.getFirstName(), userDto.getFirstName());
        Assert.assertEquals(user.getLastName(), userDto.getLastName());
        Assert.assertEquals(user.getJoiningDate(), userDto.getJoiningDate());
        Assert.assertEquals(user.getRentals(), userDto.getRentals());
    }

    @Test
    public void testMapUserListToUserDtoList() {
        //Given
        List<User> userList = new ArrayList<>();
        for(int i = 0; i<10; i++) {
            userList.add(new User((long) i, "Johnny" + i, "Swaczyna" + i, LocalDate.now().minusDays(i), new ArrayList<Rental>()));
        }
        //When
        List<UserDto> userDtoList = mapper.mapUserListToUserDtoList(userList);

        //Then
        Assert.assertEquals(userDtoList.size(), userList.size());
        for(int i = 0; i<userDtoList.size(); i++) {
            Assert.assertEquals(userList.get(i).getId(), userDtoList.get(i).getId());
            Assert.assertEquals(userList.get(i).getFirstName(), userDtoList.get(i).getFirstName());
            Assert.assertEquals(userList.get(i).getLastName(), userDtoList.get(i).getLastName());
            Assert.assertEquals(userList.get(i).getJoiningDate(), userDtoList.get(i).getJoiningDate());
            Assert.assertEquals(userList.get(i).getRentals(), userDtoList.get(i).getRentals());
        }
    }

    @Test
    public void testMapUserDtoListToUserList() {
        //Given
        List<UserDto> userDtoList = new ArrayList<>();
        for(int i = 0; i<10; i++) {
            userDtoList.add(new UserDto((long) i, "Johnny" + i, "Swaczyna" + i, LocalDate.now().minusDays(i), new ArrayList<Rental>()));
        }
        //When
        List<User> userList = mapper.mapUserDtoListToUserList(userDtoList);

        //Then
        Assert.assertEquals(userDtoList.size(), userList.size());
        for(int i = 0; i<userList.size(); i++) {
            Assert.assertEquals(userList.get(i).getId(), userDtoList.get(i).getId());
            Assert.assertEquals(userList.get(i).getFirstName(), userDtoList.get(i).getFirstName());
            Assert.assertEquals(userList.get(i).getLastName(), userDtoList.get(i).getLastName());
            Assert.assertEquals(userList.get(i).getJoiningDate(), userDtoList.get(i).getJoiningDate());
            Assert.assertEquals(userList.get(i).getRentals(), userDtoList.get(i).getRentals());
        }
    }

    @Test
    public void testMapSpecimenToSpecimenDto() {
        //Given
        BookSpecimen specimen = new BookSpecimen(5L, new Title(), "new", true, new ArrayList<Rental>());

        //When
        BookSpecimenDto specimenDto = mapper.mapSpecimenToSpecimenDto(specimen);

        //Then
        Assert.assertEquals(specimen.getTitle(), specimenDto.getTitle());
        Assert.assertEquals(specimen.getRentals(), specimenDto.getRentals());
        Assert.assertEquals(specimen.getStatus(), specimenDto.getStatus());
        Assert.assertEquals(specimen.getSpecimenId(), specimenDto.getSpecimenId());
        Assert.assertEquals(specimen.isAvailableForRent(), specimenDto.isAvailableForRent());
    }

    @Test
    public void testMapSpecimenDtoToSpecimen() {
        //Given
        BookSpecimenDto specimenDto = new BookSpecimenDto(5L, new Title(), "new", true, new ArrayList<Rental>());

        //When
        BookSpecimen specimen = mapper.mapSpecimenDtoToSpecimen(specimenDto);

        //Then
        Assert.assertEquals(specimen.getTitle(), specimenDto.getTitle());
        Assert.assertEquals(specimen.getRentals(), specimenDto.getRentals());
        Assert.assertEquals(specimen.getStatus(), specimenDto.getStatus());
        Assert.assertEquals(specimen.getSpecimenId(), specimenDto.getSpecimenId());
        Assert.assertEquals(specimen.isAvailableForRent(), specimenDto.isAvailableForRent());
    }
}
