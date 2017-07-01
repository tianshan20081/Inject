package com.gooker.routercompiler;

import com.google.auto.service.AutoService;
import com.gooker.router.annotation.Action;
import com.gooker.router.annotation.Router;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;


//@SupportedAnnotationTypes({
//        "com.gooker.router.annotation.Router",
//        "com.gooker.router.annotation.Action"})
//@SupportedOptions({"targetModuleName", "assetsDir"})
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
//@AutoService(Processor.class)
public class ProcessorA extends AbstractProcessor {


    public ProcessorA() {
        log("new RouterProcessorA()");
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
    public boolean process(Set<? extends TypeElement> annotationSet, RoundEnvironment roundEnvironment) {
        if (null == annotationSet) {
            log("-----------------------null == annotationSet");
            return false;
        }
        if (annotationSet.isEmpty()) {
            log("-----------------------annotationSet.isEmpty()");
            return false;
        }
        for (TypeElement typeElement : annotationSet) {
            Name simpleName = typeElement.getSimpleName();
            log(simpleName.toString());
        }

        log("");
        return false;
    }

    private void log(String msg) {
        System.out.println("======================" + msg);
    }
}
