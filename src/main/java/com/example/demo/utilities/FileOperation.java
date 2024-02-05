package com.example.demo.utilities;

import com.example.demo.model.enums.Format;
import com.example.demo.validation.InputValidator;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;
import org.springframework.web.multipart.MultipartFile;

public class FileOperation {

    private final MultipartFile file;

    public FileOperation(MultipartFile file) {
        this.file = file;
    }

    public String getFileFormat(){

        String filename = file.getOriginalFilename();
        assert filename != null;
        String format = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

        return format;
    }

    public String getNameWithoutExtension() {
        String filename = file.getOriginalFilename();
        assert filename != null;
        return filename.substring(0, filename.lastIndexOf("."));
    }
}
