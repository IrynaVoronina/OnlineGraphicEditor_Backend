package com.example.demo.service.image.strategy.effects;

import com.example.demo.service.image.strategy.VisualEffect;
import com.example.demo.service.image.strategy.VisualEffectStrategy;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

@Component
public class BlackAndWhiteEffect implements VisualEffectStrategy {
    @Override
    public BufferedImage applyEffect(BufferedImage bufferedImage) {
        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage grayscaleImage = op.filter(bufferedImage, null);

        BufferedImage resultImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(grayscaleImage, 0, 0, null);
        g2d.dispose();

        return resultImage;
    }

    @Override
    public VisualEffect getVisualEffect() {
        return VisualEffect.blackAndWhite;
    }
}
