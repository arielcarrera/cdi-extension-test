package com.github.arielcarrera.cdi.test.config;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.inject.Qualifier;

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
public @interface EntityOperationListener {

    @SuppressWarnings("all")
    public static class Literal extends AnnotationLiteral<EntityOperationListener> implements EntityOperationListener {

        public static final Literal INSTANCE = new Literal();

        public Literal() {
        }
    }
}