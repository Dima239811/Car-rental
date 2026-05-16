package com.infy.mapper;

import com.infy.dto.ClientBriefResponse;
import com.infy.dto.UserUpdateRequest;
import com.infy.entity.Client;
import com.infy.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserUpdateRequest toBriefResponse(User user);

    List<UserUpdateRequest> toBriefResponseList(List<User> users);
}
