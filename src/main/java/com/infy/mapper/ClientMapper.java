package com.infy.mapper;

import com.infy.dto.ClientBriefResponse;
import com.infy.dto.RegisterClientRequest;
import com.infy.dto.RequestClient;
import com.infy.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "user.login")
    @Mapping(target = "fullName", source = "user.fullName")
    ClientBriefResponse toBriefResponse(Client client);

    List<ClientBriefResponse> toBriefResponseList(List<Client> clients);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Client toEntity(RequestClient request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rentCount", constant = "0")
    @Mapping(target = "user", ignore = true)
    Client toEntity(RegisterClientRequest request);
}
