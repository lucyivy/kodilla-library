package com.kodilla.library.controller;

import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Title;
import com.kodilla.library.dto.*;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library/admin")
public class LibraryAdminController {
    @Autowired
    private DbService service;

    @Autowired
    private LibraryMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value="listUsers")
    public List<UserDto> listUsers() {
        return mapper.mapUserListToUserDtoList(service.listUsers());
    }

    @RequestMapping(method = RequestMethod.POST, value="createUser", consumes = APPLICATION_JSON_VALUE)
    public UserDto createUser(@RequestBody UserDto userDto) {
        return mapper.mapUserToUserDto(service.saveUser(mapper.mapUserDtoToUser(userDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value="deleteUser")
    public void deleteUser(@RequestParam("Id") Long Id) {
        service.deleteUser(Id);
    }

    @RequestMapping(method = RequestMethod.GET, value="listTitles")
    public List<Title> listTitles() {
        return service.findAllTitles();
    }

    @RequestMapping(method = RequestMethod.GET, value="listBooks")
    public List<BookSpecimenDto> listSpecimens() {
        List<BookSpecimenDto> toReturn = new ArrayList<>();
        for(BookSpecimen specimen : service.findSpecimens()) {
            toReturn.add(mapper.mapSpecimenToSpecimenDto(specimen));
        }
        return toReturn;
    }

    @RequestMapping(method = RequestMethod.POST, value="addBook", consumes = APPLICATION_JSON_VALUE)
    public BookSpecimenDto createBookSpecimen(@RequestBody BookSpecimenDto bookSpecimenDto) {
        return mapper.mapSpecimenToSpecimenDto(service.addSpecimen(mapper.mapSpecimenDtoToSpecimen(bookSpecimenDto)));
    }


}
