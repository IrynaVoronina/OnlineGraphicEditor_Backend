package com.example.demo.model.entities.elements;

import com.example.demo.model.enums.GraphicPrimitiveType;
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
public class GraphicPrimitiveElement extends Element {

    @Enumerated(EnumType.STRING)
    GraphicPrimitiveType type;

    public GraphicPrimitiveElement(GraphicPrimitiveElement graphicPrimitiveElement) {
        if (graphicPrimitiveElement != null) {
            this.type = graphicPrimitiveElement.type;
            super.coordinates = graphicPrimitiveElement.getCoordinates();
            this.color = graphicPrimitiveElement.color;
        }
    }

    public GraphicPrimitiveElement clone() {
        return new GraphicPrimitiveElement(this);
    }
}




