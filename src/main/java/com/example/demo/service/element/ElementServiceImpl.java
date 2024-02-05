package com.example.demo.service.element;

import com.example.demo.model.enums.GraphicPrimitiveType;
import com.example.demo.service.element.factory.GraphicPrimitiveDrawer;
import com.example.demo.service.element.factory.GraphicPrimitiveDrawerFactory;
import com.example.demo.validation.exeptions.EntityNotFoundException;
import com.example.demo.model.entities.layer.Layer;
import com.example.demo.model.entities.layer.LayerCaretaker;
import com.example.demo.model.entities.layer.LayerMemento;
import com.example.demo.model.entities.elements.Element;
import com.example.demo.model.entities.elements.GraphicPrimitiveElement;
import com.example.demo.model.entities.elements.TextElement;
import com.example.demo.repository.ElementRepository;
import com.example.demo.repository.LayerRepository;
import com.example.demo.service.element.template.*;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ElementServiceImpl implements ElementService {

    ElementRepository elementRepository;

    LayerRepository layerRepository;
    LayerCaretaker caretaker;

    GraphicPrimitiveDrawerFactory graphicPrimitiveDrawerFactory;

    @Override
    @Transactional
    public Element getById(Integer id) {
        return getOrElseThrow(id);
    }

    private Element getOrElseThrow(Integer id) {
        return elementRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Element with id %s does not exist", id)));
    }

    @Override
    @Transactional
    public Element addElement(Element element) {

        elementRepository.save(element);

        Layer layer = element.getLayer();
        LayerMemento memento = layer.saveToMemento();
        caretaker.setLayerMemento(memento);

        Layer renderedLayer = getRenderedLayer(element);

        layerRepository.save(renderedLayer);

        return element;
    }

    private Layer getRenderedLayer(Element element) {
        Layer renderedLayer = null;
        if (element instanceof TextElement textElement) {
            TextDrawer textDrawer = new TextDrawer(textElement);
            renderedLayer = textDrawer.renderLayer();
        } else if (element instanceof GraphicPrimitiveElement primitiveElement) {
            GraphicPrimitiveType type = primitiveElement.getType();
            GraphicPrimitiveDrawer graphicPrimitiveDrawer =
                    graphicPrimitiveDrawerFactory.createGraphicPrimitiveDrawer(type, primitiveElement);
            renderedLayer = graphicPrimitiveDrawer.renderLayer();
        }
        return renderedLayer;
    }


    @Override
    @Transactional
    public void deleteElement(Element element) {
        LayerMemento memento = caretaker.getLayerMemento();
        Layer layer = element.getLayer();
        layer.undoFromMemento(memento);

        elementRepository.delete(element);
        layerRepository.save(layer);
    }
}
