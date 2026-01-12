package com.smh.emailsender.interfaces.mapper;

import com.smh.emailsender.domain.model.Email;
import com.smh.emailsender.interfaces.dtos.request.EmailRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmailMapper {
    Email toDomain(EmailRequestDTO dto);
}
