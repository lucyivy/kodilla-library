package com.kodilla.library.controller;

import com.kodilla.library.domain.BookSpecimen;
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
@RequestMapping("/v1/library/user")
public class LibraryUserController {

    @Autowired
    private DbService service;

    @Autowired
    private LibraryMapper mapper;

    @RequestMapping(method = RequestMethod.GET, value="getBookSpecimens")
    public List<BookSpecimenDto> getAvailableSpecimen(@RequestParam String title) {
        List<BookSpecimenDto> toReturn = new ArrayList<BookSpecimenDto>();

        for(BookSpecimen specimen : service.findAvailableSpecimenByTitle(title)) {
            toReturn.add(mapper.mapSpecimenToSpecimenDto(specimen));
        }

        return toReturn;
    }

    @RequestMapping(method = RequestMethod.POST, value="rentBook")
    public String rentBook(@RequestParam Long specimenId, @RequestParam Long userId){
        return service.rentBook(specimenId, userId);
    }

    @RequestMapping(method = RequestMethod.POST, value="return")
    public String returnBook(@RequestParam Long rentId){
        return service.returnBook(rentId);
    }

}
