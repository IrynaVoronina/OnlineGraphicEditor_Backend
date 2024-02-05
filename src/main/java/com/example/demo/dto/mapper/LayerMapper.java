package com.example.demo.dto.mapper;

import com.example.demo.dto.LayerDto;
import com.example.demo.dto.LayerOrderResponseDto;
import com.example.demo.dto.elements.mappers.ElementMapper;

import com.example.demo.model.entities.layer.Layer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ElementMapper.class})
public interface LayerMapper {
    @Mapping(source = "image.id", target = "imageId")
    LayerDto toDto(Layer map);

    @Mapping(source = "image.id", target = "imageId")
    List<LayerDto> toDtoList(Collection<Layer> layers);


    @Mapping(source = "image", target = "image")
    LayerOrderResponseDto toLayerOrderResponseDto(Layer map);
}