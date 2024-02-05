package com.example.demo.service.image;

import com.example.demo.dto.request.SaveAsImageDto;
import com.example.demo.model.entities.Image;
import com.example.demo.model.entities.layer.Layer;
import com.example.demo.model.enums.Format;
import com.example.demo.service.layer.LayerService;
import com.example.demo.validation.InputValidator;
import com.example.demo.validation.exeptions.LocationException;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
public class ImageStorageServiceImpl implements ImageStorageService {

    private final LayerService layerService;
    private final ImageService imageService;

    @Override
    @Transactional
    public Image saveImage(Image image) {

        byte[] byteData = layerService.mergeLayers(image);

        layerService.deleteLayersByImage(image);

        Layer layer = layerService.createDefaultLayerForImage(image, byteData);
        image.setLayers(List.of(layer));

        return image;
    }

    @Override
    @Transactional
    public Image saveImageAs(SaveAsImageDto saveAsImageDto) throws UnsupportedFileFormatException {
        Image image = imageService.getById(saveAsImageDto.getId());

        String name = saveAsImageDto.getName();
        String format = saveAsImageDto.getFormat();

        if (name.isEmpty()){
            name = image.getName();
        }
        if (format.isEmpty()){
            format = String.valueOf(image.getFormat());
        }

        image.setName(name);
        InputValidator.validateFormat(format);
        image.setFormat(Format.valueOf(format));

        image.setLocation(saveAsImageDto.getLocation());

        return saveImage(image);
    }
}