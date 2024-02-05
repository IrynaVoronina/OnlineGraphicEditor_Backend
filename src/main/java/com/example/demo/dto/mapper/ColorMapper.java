package com.example.demo.dto.mapper;

import com.example.demo.dto.ColorDto;
import com.example.demo.dto.elements.mappers.ElementMapper;
import com.example.demo.model.entities.Color;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ElementMapper.class})
public interface ColorMapper {

    ColorDto toDto(Color color);

    Color toEntity(ColorDto colorDto);
}
