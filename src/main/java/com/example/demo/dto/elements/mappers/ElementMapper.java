package com.example.demo.dto.elements.mappers;

import com.example.demo.dto.elements.ElementDetailsDto;
import com.example.demo.dto.elements.ElementResponseDto;
import com.example.demo.dto.elements.GraphicPrimitiveElementDto;
import com.example.demo.dto.elements.TextElementDto;
import com.example.demo.model.entities.elements.Element;
import com.example.demo.model.entities.elements.GraphicPrimitiveElement;
import com.example.demo.model.entities.elements.TextElement;
import com.example.demo.service.color.ColorService;
import com.example.demo.service.layer.LayerService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)

public abstract class ElementMapper {

    @Autowired
    protected LayerService layerService;

    @Autowired
    protected ColorService colorService;

    @Mapping(target = "details", expression = "java(mapElementToDto(entity))")
    public abstract ElementResponseDto toDto(Element entity);

    public ElementDetailsDto mapElementToDto(Element element) {
        if (element instanceof TextElement) {
            return mapTextElementToDto((TextElement) element);
        } else if (element instanceof GraphicPrimitiveElement) {
            return mapGraphicPrimitiveElementToDto((GraphicPrimitiveElement) element);
        }
        return null;
    }

    abstract TextElementDto mapTextElementToDto(TextElement textElement);

    abstract GraphicPrimitiveElementDto mapGraphicPrimitiveElementToDto(GraphicPrimitiveElement graphicPrimitiveElement);


    @Mapping(target = "layer", expression = "java(this.layerService.getById(dto.getLayerId()))")
    @Mapping(target = "color", expression = "java(this.colorService.getById(dto.getColorId()))")
    public abstract TextElement toTextElementEntity(TextElementDto dto);


    @Mapping(target = "layer", expression = "java(this.layerService.getById(dto.getLayerId()))")
    @Mapping(target = "color", expression = "java(this.colorService.getById(dto.getColorId()))")
    public abstract GraphicPrimitiveElement toGraphicPrimitiveElementEntity(GraphicPrimitiveElementDto dto);

}
