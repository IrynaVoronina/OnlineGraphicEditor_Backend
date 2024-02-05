package com.example.demo.dto.mapper;

import com.example.demo.dto.ImageDto;
import com.example.demo.model.entities.Image;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;

import java.util.Base64;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {LayerMapper.class})
public interface ImageMapper {

    ImageDto toDto(Image image);

    @Mapping(target = "layers", ignore = true)
    Image toEntity(ImageDto imageDto);

}