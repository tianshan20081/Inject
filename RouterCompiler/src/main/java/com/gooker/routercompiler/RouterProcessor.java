package com.gooker.routercompiler;

import com.google.auto.service.AutoService;

import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Process.class)
@SupportedAnnotationTypes({
        "com.gooker.router.annotation.Router",
        "com.gooker.router.annotation.Action"})
@SupportedOptions({"targetModuleName", "assetsDir"})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class RouterProcessor extends AbstractProcessor {


    public RouterProcessor() {
        log("new RouterProcessor()");
    }


    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnv);
        log("init");
        Elements elementUtils = processingEnv.getElementUtils();
        Map<String, String> map = processingEnv.getOptions();
        Set<String> keys = map.keySet();
        for (String key : keys) {
            System.out.println(key + " = 模块 " + map.get(key));
        }

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
        System.out.println("====================================================================================================" + msg);
    }
}
