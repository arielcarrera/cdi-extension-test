package com.github.arielcarrera.cdi.test.config;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;
import javax.enterprise.util.Nonbinding;

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Target({ TYPE, METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
public @interface Listeners {

    @Nonbinding
    Class<? extends ListenerInterface>[] value();

    @SuppressWarnings("all")
    public static class Literal extends AnnotationLiteral<Listeners> implements Listeners {

        public static final Literal INSTANCE = new Literal(null);

        private final Class<? extends ListenerInterface>[] value;

        public Literal(Class<? extends ListenerInterface>[] clazzArray) {
            this.value = clazzArray;
        }

        @Override
        public Class<? extends ListenerInterface>[] value() {
            return value;
        }
    }
}