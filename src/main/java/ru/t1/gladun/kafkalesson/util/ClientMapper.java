package ru.t1.gladun.kafkalesson.util;

import org.springframework.stereotype.Component;
import ru.t1.gladun.kafkalesson.dto.ClientDto;
import ru.t1.gladun.kafkalesson.entity.Client;

@Component
public class ClientMapper {
    public static Client toEntity(ClientDto dto) {
        return Client.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .fullName(dto.getFullName())
                .phoneNumber(dto.getPhoneNumber())
                .homeAddress(dto.getHomeAddress())
                .education(dto.getEducation())
                .carModel(dto.getCarModel())
                .build();

    }

    public static ClientDto toDto(Client entity) {
        return ClientDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .fullName(entity.getFullName())
                .phoneNumber(entity.getPhoneNumber())
                .homeAddress(entity.getHomeAddress())
                .education(entity.getEducation())
                .carModel(entity.getCarModel())
                .build();
    }
}
