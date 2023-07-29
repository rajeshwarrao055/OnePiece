package com.personal.OnePiece.annotation;

import com.personal.OnePiece.annotation.model.Entity;
import org.springframework.stereotype.Service;

@TestAnnotation
@Service
public class AnnotationChecker {
    public void invoke() {
        Entity entity = new Entity();

    }
}
