package com.personal.OnePiece.annotation;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Set;

@SupportedAnnotationTypes("com.personal.OnePiece.annotation.RGetter")
@SupportedSourceVersion(SourceVersion.RELEASE_17)
public class RGetterAnnotationProcessor extends AbstractProcessor {
    /**
     * {@inheritDoc}
     *
     * @param annotations
     * @param roundEnv
     */
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(Element element : roundEnv.getElementsAnnotatedWith(RGetter.class)) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "@RGetter annotation can be applied only to fields", element);
            continue;
        }

        return true;
    }

    private void generateRGetter(TypeElement classElement, Element fieldElement, ProcessingEnvironment processingEnvironment) {
        String fieldName = fieldElement.getSimpleName().toString();
        String getterName = "rget" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
        StringBuilder rgetterCode = new StringBuilder();
        rgetterCode.append("public ");
        rgetterCode.append(fieldElement.asType().toString());
        rgetterCode.append(" ");
        rgetterCode.append(getterName);
        rgetterCode.append("() { return this.");
        rgetterCode.append(fieldName);
        rgetterCode.append("; }");
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "Generating rgetter for field : " + fieldName);

        String packageName = processingEnvironment.getElementUtils().getPackageOf(classElement).toString();
        String className = classElement.getSimpleName().toString();

        try {
            JavaFileObject sourceFile = processingEnvironment.getFiler()
                    .createSourceFile(packageName + "." + className);

            try (Writer writer = sourceFile.openWriter()) {
                writer.append("package ").append(packageName).append(";\n\n");
                writer.append("public class ").append(className).append(" {\n\n");
                writer.append(rgetterCode).append("\n\n");
                writer.append("}\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            processingEnvironment.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "Failed to write generated code to file: " + e.getMessage());
        }
    }
}
