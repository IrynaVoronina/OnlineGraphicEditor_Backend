package com.example.demo.service.image;

import com.example.demo.dto.request.SaveAsImageDto;
import com.example.demo.model.entities.Image;
import com.example.demo.utilities.FileSystemOperation;
import com.example.demo.validation.exeptions.ExistenceException;
import com.example.demo.validation.exeptions.LocationException;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service("ImageStorageServiceProxy")
public class ImageStorageServiceProxy implements ImageStorageService {


    private final ImageStorageService imageStorageService;

    private final FileSystemOperation fileSystemOperation;


    public ImageStorageServiceProxy(@Qualifier("imageStorageServiceImpl") ImageStorageService imageStorageService,
                                    FileSystemOperation fileSystemOperation) {
        this.imageStorageService = imageStorageService;
        this.fileSystemOperation = fileSystemOperation;
    }


    @Override
    @Transactional
    public Image saveImage(Image image) throws IOException, LocationException, ExistenceException {

        String location = image.getLocation();

        if (location == null) {
            throw new LocationException("Specify the location");
        } else {

            imageStorageService.saveImage(image);
            fileSystemOperation.saveImageToFileSystem(image);

            return image;
        }
    }

    @Override
    public Image saveImageAs(SaveAsImageDto saveAsImageDto) throws IOException, ExistenceException, UnsupportedFileFormatException {

        Image image = imageStorageService.saveImageAs(saveAsImageDto);
        String filePath = fileSystemOperation.getFilePath(image);

        if (Files.exists(Path.of(filePath))) {
            throw new ExistenceException("Image with the same name already exists in this directory");
        }

        fileSystemOperation.saveImageToFileSystem(image);

        return image;
    }
}

