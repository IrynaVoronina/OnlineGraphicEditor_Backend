package com.example.demo.model.entities.layer;

import com.example.demo.model.entities.Image;
import com.example.demo.model.entities.elements.Element;
import com.example.demo.model.entities.elements.GraphicPrimitiveElement;
import com.example.demo.model.entities.elements.TextElement;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Layer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    int position;

    @ManyToOne()
    @JoinColumn(name = "imageId")
    Image image;

    @Basic(fetch = FetchType.LAZY)
    @Column(name = "byte_data", columnDefinition = "bytea")
    byte[] byteData;

    @OneToMany(mappedBy = "layer", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Element> elements;


    public Layer(Layer layer) {
        this.image = layer.image;
        this.byteData = layer.byteData;
        this.elements = getListOfClonedElements(layer);
    }

    public Layer clone() {
        return new Layer(this);
    }


    private List<Element> getListOfClonedElements(Layer layer) {
        ArrayList<Element> listOfClonedElements = new ArrayList<>();
        layer.elements.forEach(element -> {
            Element clonedElement = null;

            if (element instanceof TextElement textElement) {
                clonedElement = textElement.clone();
            } else if (element instanceof GraphicPrimitiveElement graphicPrimitiveElement) {
                clonedElement = graphicPrimitiveElement.clone();
            }

            if (clonedElement != null) {
                clonedElement.setLayer(this);
                listOfClonedElements.add(clonedElement);
            }
        });
        return listOfClonedElements;
    }


    public LayerMemento saveToMemento() {
        return new LayerMemento(this.id,
                this.position, this.image, this.byteData, this.elements);
    }

    public void undoFromMemento(LayerMemento memento) {
        this.id = memento.getId();
        this.position = memento.getPosition();
        this.image = memento.getImage();
        this.byteData = memento.getByteData();
        this.elements = memento.getElements();
    }
}

