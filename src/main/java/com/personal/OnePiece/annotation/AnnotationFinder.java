package com.personal.OnePiece.annotation;

import com.google.common.reflect.ClassPath;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
public class AnnotationFinder {
    public List<Class<?>> findClassesWithAnnotation(String packageName) {
        List<Class<?>> annotatedClasses = new ArrayList<>();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Class<? extends Annotation> annotationClass = (Class<? extends Annotation>) classLoader.loadClass("com.personal.OnePiece.annotation.TestAnnotation");
            for(ClassPath.ClassInfo classInfo : ClassPath.from(classLoader).getTopLevelClassesRecursive(packageName)) {
                Class<?> clazz = classInfo.load();
                if(clazz.getAnnotations().length > 0) {
                    for(Annotation annotation : clazz.getAnnotations()) {
                        if(annotation.annotationType().getName().equals(annotationClass.getName())) {
                            annotatedClasses.add(clazz);
                            break;
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            log.error(ex);
        }
        return annotatedClasses;
    }
}
