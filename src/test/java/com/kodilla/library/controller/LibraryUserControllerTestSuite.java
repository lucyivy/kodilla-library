package com.kodilla.library.controller;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;
import java.util.*;

import com.kodilla.library.domain.Title;
import com.kodilla.library.dto.BookSpecimenDto;
import com.kodilla.library.service.DbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.google.gson.Gson;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.User;
import com.kodilla.library.dto.UserDto;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryUserController.class)
public class LibraryUserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LibraryUserController controller;

    @Test
    public void testGetAvailableSpecimen() throws Exception {
        //Given
        List<BookSpecimenDto> specimens = new ArrayList<>();
        specimens.add(new BookSpecimenDto());
        when(controller.getAvailableSpecimen(ArgumentMatchers.any(String.class))).thenReturn(specimens);

        //When&Then
        mockMvc.perform(get("/v1/library/user/getBookSpecimens").contentType(MediaType.APPLICATION_JSON)
                .param("title", ""))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$" , hasSize(1)));
    }

    @Test
    public void testRentBook() throws  Exception{
        //Given
        when(controller.rentBook(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn("Success");

        //When&Then
        mockMvc.perform(post("/v1/library/user/rentBook")
                .contentType(MediaType.APPLICATION_JSON)
                .param("specimenId", "5")
                .param("userId", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("Success"));
    }

    @Test
    public void testReturnBook() throws  Exception{
        //Given
        when(controller.returnBook(ArgumentMatchers.any(Long.class))).thenReturn("Success");

        //When&Then
        mockMvc.perform(post("/v1/library/user/return")
                .contentType(MediaType.APPLICATION_JSON)
                .param("rentId", "4"))
                .andExpect(status().is(200))
                .andExpect(content().string("Success"));
    }
}
