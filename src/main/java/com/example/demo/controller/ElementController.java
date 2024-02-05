package com.example.demo.controller;

import com.example.demo.dto.elements.ElementResponseDto;
import com.example.demo.dto.elements.GraphicPrimitiveElementDto;
import com.example.demo.dto.elements.TextElementDto;

import com.example.demo.dto.elements.mappers.ElementMapper;
import com.example.demo.model.entities.elements.Element;
import com.example.demo.model.entities.elements.GraphicPrimitiveElement;
import com.example.demo.model.entities.elements.TextElement;
import com.example.demo.service.element.ElementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/element")
public class ElementController {

    ElementMapper elementMapper;
    ElementService elementService;


    @DeleteMapping("/{elementId}")
    public ResponseEntity<Void> deleteTextElement(@PathVariable Integer elementId) {
        elementService.deleteElement(elementService.getById(elementId));
        return ResponseEntity.ok().build();
    }


    @PutMapping(value = "/addText")
    public ResponseEntity<ElementResponseDto> addText(@RequestBody TextElementDto textElementDto) {

        TextElement textElementEntity = elementMapper.toTextElementEntity(textElementDto);
        Element addedElement = elementService.addElement(textElementEntity);
        ElementResponseDto elementResponseDto = elementMapper.toDto(addedElement);

        return ResponseEntity.ok().body(elementResponseDto);
    }


    @PutMapping(value = "/addGraphicPrimitive")
    public ResponseEntity<ElementResponseDto> addGraphicPrimitive(
            @RequestBody GraphicPrimitiveElementDto graphicPrimitiveElementDto) {

        GraphicPrimitiveElement entity = elementMapper.toGraphicPrimitiveElementEntity(graphicPrimitiveElementDto);
        Element addedElement = elementService.addElement(entity);
        ElementResponseDto elementResponseDto = elementMapper.toDto(addedElement);

        return ResponseEntity.ok().body(elementResponseDto);
    }
}




