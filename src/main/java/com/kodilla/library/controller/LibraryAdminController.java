package com.kodilla.library.controller;

import com.kodilla.library.dto.*;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.DbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
    public void deleteUser(@RequestParam Long Id) {
        service.deleteUser(Id);
    }
}
