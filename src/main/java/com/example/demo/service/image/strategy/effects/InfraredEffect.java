package com.example.demo.service.image.strategy.effects;

import com.example.demo.service.image.strategy.VisualEffect;
import com.example.demo.service.image.strategy.VisualEffectStrategy;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class InfraredEffect implements VisualEffectStrategy {
    @Override
    public BufferedImage applyEffect(BufferedImage bufferedImage) {
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int invertedRed = 255 - red;
                int invertedGreen = 255 - green;
                int invertedBlue = 255 - blue;

                int newRGB = (alpha << 24) | (invertedRed << 16) | (invertedGreen << 8) | invertedBlue;
                bufferedImage.setRGB(x, y, newRGB);
            }
        }
        return bufferedImage;
    }

    @Override
    public VisualEffect getVisualEffect() {
        return VisualEffect.infrared;
    }
}
