package com.kodilla.library.controller;


import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.Title;
import com.kodilla.library.dto.BookSpecimenDto;
import com.kodilla.library.mapper.LibraryMapper;
import com.kodilla.library.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryUserController.class)
public class LibraryUserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryMapper mapper;

    @MockBean
    private DbService service;

    @Test
    public void testGetAvailableSpecimen() throws Exception {
        //Given
        BookSpecimen specimen = new BookSpecimen(5L, new Title(), "new", true, new ArrayList<Rental>());
        List<BookSpecimen> resultList = new ArrayList<>();
        resultList.add(specimen);
        when(service.findAvailableSpecimenByTitle(ArgumentMatchers.any())).thenReturn(resultList);
        when(mapper.mapSpecimenToSpecimenDto(ArgumentMatchers.any())).thenReturn(new BookSpecimenDto(5L, new Title(), "new", true, new ArrayList<Rental>()));

        //When&Then
        mockMvc.perform(get("/v1/library/user/getBookSpecimens").contentType(MediaType.APPLICATION_JSON)
                .param("title", "1"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testRentBook() throws Exception {
        //Given
        when(service.rentBook(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn("Success");

        //When&Then
        mockMvc.perform(post("/v1/library/user/rentBook")
                .contentType(MediaType.APPLICATION_JSON)
                .param("specimenId", "5")
                .param("userId", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("Success"));
    }

    @Test
    public void testReturnBook() throws Exception {
        //Given
        when(service.returnBook(ArgumentMatchers.any(Long.class))).thenReturn("Success");

        //When&Then
        mockMvc.perform(post("/v1/library/user/return")
                .contentType(MediaType.APPLICATION_JSON)
                .param("rentId", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("Success"));
    }
}
