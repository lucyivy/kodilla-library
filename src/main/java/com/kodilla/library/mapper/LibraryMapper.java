package com.kodilla.library.mapper;

import com.kodilla.library.dto.*;
import com.kodilla.library.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class LibraryMapper {

    public UserDto mapUserToUserDto(User user) {
        return new UserDto(user.getId(),
                            user.getFirstName(),
                            user.getLastName(),
                            user.getJoiningDate(),
                            user.getRentals());
    }

    public User mapUserDtoToUser(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getJoiningDate(),
                userDto.getRentals());
    }

    public List<UserDto> mapUserListToUserDtoList(List<User> users) {
        List<UserDto> resultList = new ArrayList<>();
        for(User user : users) {
            resultList.add(mapUserToUserDto(user));
        }
        return resultList;
    }

    public List<User> mapUserDtoListToUserList(List<UserDto> users) {
        List<User> resultList = new ArrayList<>();
        for(UserDto user : users) {
            resultList.add(mapUserDtoToUser(user));
        }
        return resultList;
    }

    public BookSpecimenDto mapSpecimenToSpecimenDto(BookSpecimen specimen) {
        return new BookSpecimenDto(specimen.getSpecimenId(),
                specimen.getTitle(),
                specimen.getStatus(),
                specimen.isAvailableForRent(),
                specimen.getRentals());
    }

    public BookSpecimen mapSpecimenDtoToSpecimen(BookSpecimenDto specimenDto) {
        return new BookSpecimen(specimenDto.getSpecimenId(),
                specimenDto.getTitle(),
                specimenDto.getStatus(),
                specimenDto.isAvailableForRent(),
                specimenDto.getRentals());
    }



}
