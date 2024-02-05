package com.example.demo.service.image.strategy.effects;

import com.example.demo.service.image.strategy.VisualEffect;
import com.example.demo.service.image.strategy.VisualEffectStrategy;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class RedColoredEffect implements VisualEffectStrategy {
    @Override
    public BufferedImage applyEffect(BufferedImage bufferedImage) {
        for (int y = 0; y < bufferedImage.getHeight(); y++) {
            for (int x = 0; x < bufferedImage.getWidth(); x++) {

                int rgb = bufferedImage.getRGB(x, y);

                int alpha = (rgb >> 24) & 0xff;
                int red = (rgb >> 16) & 0xff;

                rgb = (alpha << 24) | (red << 16) | (0 << 8) | 0;

                bufferedImage.setRGB(x, y, rgb);
            }
        }
        return bufferedImage;
    }

    @Override
    public VisualEffect getVisualEffect() {
        return VisualEffect.redColored;
    }
}
