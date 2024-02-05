package com.example.demo.service.image;

import com.example.demo.model.entities.Image;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface ImageService {

    Image getById(Integer id);

    Image createImage(Image image) throws IOException;

    public Image uploadImage(MultipartFile file) throws IOException, UnsupportedFileFormatException;

    Image applyVisualEffect(Image image, String effect) throws IOException;

}
