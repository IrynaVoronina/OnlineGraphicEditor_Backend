package com.example.demo.service.image.strategy;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Map;


@Component
@AllArgsConstructor
public class VisualEffectContext {
    private final Map<VisualEffect, VisualEffectStrategy> visualEffectMap;

    public BufferedImage applyEffect(BufferedImage image, VisualEffect visualEffect) {
        if (visualEffectMap.containsKey(visualEffect)) {
            return visualEffectMap.get(visualEffect).applyEffect(image);
        } else {
            throw new IllegalArgumentException("There is no such effect");
        }
    }
}