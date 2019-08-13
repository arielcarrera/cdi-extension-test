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

import com.github.arielcarrera.cdi.test.FooInterface;

@Qualifier
@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
public @interface Listener {

    Class<? extends FooInterface> value();

    @SuppressWarnings("all")
    public static class Literal extends AnnotationLiteral<Listener> implements Listener {

        public static final Literal INSTANCE = new Literal(null);

        private final Class<? extends FooInterface> value;

        public Literal(Class<? extends FooInterface> clazz) {
            this.value = clazz;
        }

        @Override
        public Class<? extends FooInterface> value() {
            return value;
        }
    }
}