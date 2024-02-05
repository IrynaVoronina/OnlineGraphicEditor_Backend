package com.example.demo.service.image.strategy;

import java.awt.image.BufferedImage;

public interface VisualEffectStrategy {

    BufferedImage applyEffect(BufferedImage bufferedImage);

    VisualEffect getVisualEffect();

}
