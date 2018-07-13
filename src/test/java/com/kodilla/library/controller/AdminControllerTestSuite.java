package com.kodilla.library.controller;

import com.google.gson.Gson;
import com.kodilla.library.domain.BookSpecimen;
import com.kodilla.library.domain.Rental;
import com.kodilla.library.domain.Title;
import com.kodilla.library.domain.User;
import com.kodilla.library.dto.BookSpecimenDto;
import com.kodilla.library.dto.UserDto;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(LibraryAdminController.class)
public class AdminControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DbService service;

    @MockBean
    private LibraryMapper mapper;

    @Test
    public void testListUsers() throws Exception {
        //Given
        List<User> userList = new ArrayList<>();
        userList.add(new User());
        List<UserDto> resultList = new ArrayList<>();
        resultList.add(new UserDto("firstname", "lastname"));
        when(service.listUsers()).thenReturn(userList);
        when(mapper.mapUserListToUserDtoList(ArgumentMatchers.any())).thenReturn(resultList);

        //When&Then
        mockMvc.perform(get("/v1/library/admin/listUsers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testCreateUser() throws Exception {
        //Given
        List<Rental> list = new ArrayList();
        UserDto userDto = new UserDto("TestName", "TestSurname");
        when(service.saveUser(ArgumentMatchers.any())).thenReturn(new User(5L, "TestName", "TestSurname", LocalDate.now(), list));
        when(mapper.mapUserToUserDto(ArgumentMatchers.any(User.class))).thenReturn(userDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(userDto);

        //When&Then
        mockMvc.perform(post("/v1/library/admin/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.firstName", is("TestName")))
                .andExpect(jsonPath("$.lastName", is("TestSurname")));
    }

    @Test
    public void testDeleteUser() throws Exception {
        //Given,When & Then
        mockMvc.perform(delete("/v1/library/admin/deleteUser")
                .contentType(MediaType.APPLICATION_JSON)
                .param("Id", "5"))
                .andExpect(status().is(200));
    }


    @Test
    public void testListTitles() throws Exception {
        //Given
        List<Title> titles = new ArrayList<>();
        titles.add(new Title());
        when(service.findAllTitles()).thenReturn(titles);

        //When&Then
        mockMvc.perform(get("/v1/library/admin/listTitles").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testListBooks() throws Exception {
        //Given
        List<BookSpecimen> specimens = new ArrayList<>();
        specimens.add(new BookSpecimen());
        when(service.findSpecimens()).thenReturn(specimens);

        //When&Then
        mockMvc.perform(get("/v1/library/admin/listBooks").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testAddBook() throws Exception {
        //Given
        BookSpecimen specimen = new BookSpecimen(5L, new Title(), "new", true, new ArrayList<Rental>());
        when(service.addSpecimen(ArgumentMatchers.any())).thenReturn(specimen);
        when(mapper.mapSpecimenToSpecimenDto(ArgumentMatchers.any())).thenReturn(new BookSpecimenDto(5L, new Title(), "new", true, new ArrayList<Rental>()));
        Gson gson = new Gson();
        String jsonContent = gson.toJson(specimen);

        //When&Then
        mockMvc.perform(post("/v1/library/admin/addBook")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.availableForRent", is(true)))
                .andExpect(jsonPath("$.specimenId", is(5)));
    }
}