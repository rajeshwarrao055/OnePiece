package com.personal.OnePiece.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/annotation")
@RestController
public class AnnotationCheckController {

    @Autowired
    private AnnotationFinder annotationFinder;

    @GetMapping("/fetch-annotated")
    public List<String> fetchAnnotatedClassNames() {
        return annotationFinder.findClassesWithAnnotation("com.personal.OnePiece.annotation")
                .stream().map(clazz -> clazz.getSimpleName()).toList();
    }
}
