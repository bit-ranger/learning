package com.bit.ranger.javac;

import javax.tools.*;
import java.io.File;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

public class Jsr199Main {

    public static void main(String[] args) throws Exception {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.CHINESE, StandardCharsets.UTF_8);

        File file = new File("/Download/javac/Hello.java");
        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(Arrays.asList(file));

        JavaCompiler.CompilationTask task = compiler.getTask(
                null,
                fileManager,
                diagnostics,
                Arrays.asList("-d", "/Doc/MyRepo/learning-java/compiler/target/classes",
                        "-processor", "lombok.launch.AnnotationProcessorHider$AnnotationProcessor"),
                null,
                compilationUnits);

        task.call();

        for (Diagnostic<? extends JavaFileObject> diagnostic : diagnostics.getDiagnostics()) {
            System.out.format("Error on line %d in %s\n%s\n",
                    diagnostic.getLineNumber(), diagnostic.getSource().toUri(), diagnostic.getMessage(null));
        }

        fileManager.close();

        Class clz = Class.forName("Hello");
        Method main = clz.getMethod("main", String[].class);
        args = new String[]{"aaa", "bbb"};
        main.invoke(clz, (Object) args);
    }
}