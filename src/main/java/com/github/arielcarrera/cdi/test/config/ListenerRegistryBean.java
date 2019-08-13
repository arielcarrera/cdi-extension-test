package com.github.arielcarrera.cdi.test.config;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.InjectionPoint;

public class ListenerRegistryBean implements Bean<ListenerRegistry> {

    ListenerRegistry registry = null;
    
    public ListenerRegistryBean(ListenerRegistry registry) {
	super();
	this.registry = registry;
    }

    @Override
    public Class<? extends Annotation> getScope() {
	return ApplicationScoped.class;
    }

    @Override
    public Set<Annotation> getQualifiers() {
	return Collections.<Annotation>singleton(Default.Literal.INSTANCE);
    }

    @Override
    public Set<Type> getTypes() {
	return Collections.<Type>singleton(ListenerRegistry.class);
    }

    @Override
    public ListenerRegistry create(
	    CreationalContext<ListenerRegistry> creationalContext) {
	return registry;
    }

    @Override
    public void destroy(ListenerRegistry instance,
	    CreationalContext<ListenerRegistry> creationalContext) {
    }

    @Override
    public Class<?> getBeanClass() {
	return ListenerRegistry.class;
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
	return Collections.emptySet();
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
	return Collections.emptySet();
    }

    @Override
    public String getName() {
	return null;
    }

    @Override
    public boolean isAlternative() {
	return false;
    }

    @Override
    public boolean isNullable() {
	return false;
    }

}
