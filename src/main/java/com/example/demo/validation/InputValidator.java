package com.example.demo.validation;

import com.example.demo.validation.exeptions.InvalidOrderException;
import com.example.demo.validation.exeptions.UnsupportedFileFormatException;

import java.util.Map;

public class InputValidator {

    public static void validateLayerOrders(Map<Integer, Integer> layerPositions, int layerOrderListSize) throws InvalidOrderException {
        if (layerPositions.size() != layerOrderListSize) {
            throw new InvalidOrderException("Duplicate positions are not allowed");
        } else {
            for (Map.Entry<Integer, Integer> entry : layerPositions.entrySet()) {
                Integer newPosition = entry.getKey();
                Integer layerIdForNewPosition = entry.getValue();

                if (newPosition <= 0 || newPosition > layerOrderListSize) {
                    throw new InvalidOrderException("Incorrect positions for layer " + layerIdForNewPosition);
                }
            }
        }
    }

    public static void validateFormat(String format) throws UnsupportedFileFormatException {
        if (format!=null){
            if (!format.equalsIgnoreCase("PNG") &&
                    !format.equalsIgnoreCase("JPG") &&
                    !format.equalsIgnoreCase("BMP")) {
                throw new UnsupportedFileFormatException("Unsupported file format. Only BMP, PNG and JPG formats are allowed.");
            }
        }
    }
}
