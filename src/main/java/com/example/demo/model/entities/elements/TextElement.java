package com.example.demo.model.entities.elements;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@PrimaryKeyJoinColumn(name = "elementId")
public class TextElement extends Element {

    String fontName;

    int size;

    String text;

    public TextElement(TextElement textElement) {
        if (textElement != null) {
            this.fontName = textElement.fontName;
            this.size = textElement.size;
            this.text = textElement.text;
            super.coordinates = textElement.getCoordinates();
            this.color = textElement.color;
        }
    }

    public TextElement clone() {
        return new TextElement(this);
    }
}


