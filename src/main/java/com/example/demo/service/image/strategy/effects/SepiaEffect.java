package com.example.demo.service.image.strategy.effects;

import com.example.demo.service.image.strategy.VisualEffect;
import com.example.demo.service.image.strategy.VisualEffectStrategy;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class SepiaEffect implements VisualEffectStrategy {
    @Override
    public BufferedImage applyEffect(BufferedImage bufferedImage) {
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {
                int rgb = bufferedImage.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xFF;
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = rgb & 0xFF;

                int newRed = (int) (0.393 * red + 0.769 * green + 0.189 * blue);
                int newGreen = (int) (0.349 * red + 0.686 * green + 0.168 * blue);
                int newBlue = (int) (0.272 * red + 0.534 * green + 0.131 * blue);

                newRed = Math.min(255, Math.max(0, newRed));
                newGreen = Math.min(255, Math.max(0, newGreen));
                newBlue = Math.min(255, Math.max(0, newBlue));

                int newRGB = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                bufferedImage.setRGB(x, y, newRGB);
            }
        }
        return bufferedImage;
    }

    @Override
    public VisualEffect getVisualEffect() {
        return VisualEffect.sepia;
    }
}
