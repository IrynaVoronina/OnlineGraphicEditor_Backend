package com.example.demo.service.color;

import com.example.demo.validation.exeptions.EntityNotFoundException;
import com.example.demo.model.entities.Color;
import com.example.demo.repository.ColorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ColorServiceImpl implements ColorService {

    private final ColorRepository colorRepository;

    @Override
    @Transactional
    public Color getById(Integer id) {
        return getOrElseThrow(id);
    }


    private Color getOrElseThrow(Integer id) {
        return colorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Color with id %s does not exist", id)));
    }

    @Override
    public Color createOrGetColor(Color color) {
        Color existingColor = colorRepository.findByHexCode(color.getHexCode());

        if (existingColor != null) {
            return existingColor;
        } else {
            colorRepository.save(color);
        }
        return color;
    }
}
