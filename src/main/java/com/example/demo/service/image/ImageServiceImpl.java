package com.example.demo.service.image;

import com.example.demo.service.image.strategy.VisualEffectContext;
import com.example.demo.utilities.FileOperation;
import com.example.demo.validation.InputValidator;
import com.example.demo.validation.exeptions.EntityNotFoundException;
import com.example.demo.model.entities.Image;
import com.example.demo.model.entities.layer.Layer;
import com.example.demo.model.enums.Format;
import com.example.demo.service.image.strategy.VisualEffect;
import com.example.demo.repository.ImageRepository;

import com.example.demo.repository.LayerRepository;
import com.example.demo.service.layer.LayerService;
import com.example.demo.utilities.ByteLayerProcessor;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;
    private final LayerService layerService;
    private final LayerRepository layerRepository;
    private final VisualEffectContext visualEffectContext;

    @Override
    @Transactional
    public Image getById(Integer id) {
        return getOrElseThrow(id);
    }


    private Image getOrElseThrow(Integer imageId) {
        return imageRepository.findById(imageId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Image with id %s does not exist", imageId)));
    }

    @Override
    @Transactional
    public Image createImage(Image image) {

        Image newImage = new Image();
        newImage.setName(image.getName());
        newImage.setWidth(image.getWidth());
        newImage.setHeight(image.getHeight());
        newImage.setFormat(image.getFormat());

        Layer layer = layerService.createDefaultLayerForImage(newImage, null);

        newImage.setLayers(List.of(layer));

        imageRepository.save(newImage);

        return newImage;
    }


    @Override
    @Transactional
    public Image uploadImage(MultipartFile file) throws IOException, UnsupportedFileFormatException {

        if (file == null) {
            throw new NullPointerException("file is null");
        }

        FileOperation fileOperation = new FileOperation(file);

        String format = fileOperation.getFileFormat();
        InputValidator.validateFormat(format);

        byte[] fileData = file.getBytes();

        Image image = new Image();
        image.setName(fileOperation.getNameWithoutExtension());

        BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        image.setWidth(width);
        image.setHeight(height);


        image.setFormat(Format.valueOf(format.toLowerCase()));

        image.setLocation(null); // не повний шлях, бо хттп блокує задля безпеки

        Layer layer = layerService.createDefaultLayerForImage(image, fileData);
        image.setLayers(List.of(layer));

        image = imageRepository.save(image);

        return image;
    }


    @Override
    public Image applyVisualEffect(Image image, String effect) {

        for (Layer layer : image.getLayers()) {
            ByteLayerProcessor byteLayerProcessor = new ByteLayerProcessor(layer);
            BufferedImage bufferedImage = byteLayerProcessor.getImageFromBytes();

            //BufferedImage newImage = applyCertainEffect(bufferedImage, effect);
            BufferedImage newImage = visualEffectContext.applyEffect(bufferedImage, VisualEffect.valueOf(effect));

            byteLayerProcessor.setByteData(newImage, "PNG");
            layerRepository.save(layer);
        }
        imageRepository.save(image);
        return image;
    }
/*
    private BufferedImage applyCertainEffect(BufferedImage bufferedImage, String effect) {
        VisualEffect visualEffect = VisualEffect.valueOf(effect);
        return switch (visualEffect) {
            case blackAndWhite -> applyBlackAndWhiteEffect(bufferedImage);
            case infrared -> applyInfraredEffect(bufferedImage);
            case sepia -> applySepiaEffect(bufferedImage);
            case redColored -> applyRedColoredEffect(bufferedImage);
            default -> throw new IllegalArgumentException("There is no such effect");
        };
    }


    private BufferedImage applyBlackAndWhiteEffect(BufferedImage bufferedImage) {

        ColorConvertOp op = new ColorConvertOp(ColorSpace.getInstance(ColorSpace.CS_GRAY), null);
        BufferedImage grayscaleImage = op.filter(bufferedImage, null);

        BufferedImage resultImage = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resultImage.createGraphics();
        g2d.drawImage(grayscaleImage, 0, 0, null);
        g2d.dispose();

        return resultImage;
    }


    private BufferedImage applyInfraredEffect(BufferedImage bufferedImage) {

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

    private BufferedImage applySepiaEffect(BufferedImage bufferedImage) {
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


    private BufferedImage applyRedColoredEffect(BufferedImage bufferedImage) {
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

 */
}

