package com.example.demo.service.color;

import com.example.demo.model.entities.Color;

public interface ColorService {

    Color getById(Integer id);

    Color createOrGetColor(Color color);
}
