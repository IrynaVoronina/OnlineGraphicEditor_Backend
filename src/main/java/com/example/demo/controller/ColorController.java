package com.example.demo.controller;

import com.example.demo.dto.ColorDto;
import com.example.demo.dto.mapper.ColorMapper;
import com.example.demo.model.entities.Color;
import com.example.demo.service.color.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping("/color")
public class ColorController {

    ColorMapper colorMapper;
    ColorService colorService;

    @PostMapping(value = "/setColor")
    public ResponseEntity<ColorDto> setColor(@RequestBody ColorDto colorDto) {

        Color colorEntity = colorMapper.toEntity(colorDto);
        Color color = colorService.createOrGetColor(colorEntity);
        ColorDto dto = colorMapper.toDto(color);

        return ResponseEntity.ok().body(dto);
    }
}
