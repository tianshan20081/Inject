package com.gooker.routercompiler;

import com.google.auto.service.AutoService;
import com.gooker.router.annotation.Action;
import com.gooker.router.annotation.Router;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


//@SupportedAnnotationTypes({
//        "com.gooker.router.annotation.Router",
//        "com.gooker.router.annotation.Action"})
//@SupportedOptions({"targetModuleName", "assetsDir"})
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class RouterProcessor extends AbstractProcessor {


    public RouterProcessor() {
        log("new RouterProcessor()");
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.RELEASE_8;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annontations = new LinkedHashSet<>();
        annontations.add(Action.class.getCanonicalName());
        annontations.add(Router.class.getCanonicalName());
        log(Action.class.getCanonicalName());
        return annontations;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Elements elementUtils = processingEnv.getElementUtils();
        Map<String, String> options = processingEnv.getOptions();
        if (options == null || options.isEmpty()) {
            return;
        }
        Set<Map.Entry<String, String>> entrySet = options.entrySet();
        for (Map.Entry<String, String> entry : entrySet) {
//            log(entry.getKey() + "\t:\t" + entry.getValue());
        }

    }

    @Override
    public boolean process(Set<? extends TypeElement> annotationSet, RoundEnvironment roundEnvironment) {
        log("annotationSet.size()\t" + annotationSet.size());
        log(new File(".").getAbsolutePath());
        log("Processor finish:\t" + roundEnvironment.processingOver());


        if (null != annotationSet && !annotationSet.isEmpty()) {
            for (TypeElement typeElement : annotationSet) {
                if (typeElement.getClass().getCanonicalName().equalsIgnoreCase(Action.class.getCanonicalName())) {
                    Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(typeElement);
                    if (null != elements && !elements.isEmpty()) {
                        for (Element element : elements) {
                            Action annotation = element.getAnnotation(Action.class);
                            String[] value = annotation.value();
                            log(value.toString());
                        }
                    }
                }
            }
        }

        return false;
    }

    private void log(String msg) {
        System.out.println("=================" + msg + "=================");
    }
}
