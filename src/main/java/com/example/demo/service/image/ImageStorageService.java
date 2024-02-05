package com.example.demo.service.image;

import com.example.demo.dto.request.SaveAsImageDto;
import com.example.demo.model.entities.Image;
import com.example.demo.validation.exeptions.ExistenceException;
import com.example.demo.validation.exeptions.LocationException;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;

import java.io.IOException;

public interface ImageStorageService {

    Image saveImage(Image image) throws IOException, LocationException, ExistenceException;

    Image saveImageAs(SaveAsImageDto saveAsImageDto) throws IOException, ExistenceException, UnsupportedFileFormatException;

}
