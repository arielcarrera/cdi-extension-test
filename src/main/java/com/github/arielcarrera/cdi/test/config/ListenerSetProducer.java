package com.github.arielcarrera.cdi.test.config;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.DefinitionException;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;

import com.github.arielcarrera.cdi.test.EntityOperationListenerInterface;
import com.github.arielcarrera.cdi.test.EntitySelectionListenerInterface;
import com.github.arielcarrera.cdi.test.ListenerInterface;

class ListenerSetProducer<S extends ListenerSet> implements Producer<S> {
    final Producer<S> decorate;
    final Listeners annotation;
    final BeanManager beanManager;

    ListenerSetProducer(Producer<S> decorate, Listeners annotation, BeanManager bm) {
	this.decorate = decorate;
	this.annotation = annotation;
	this.beanManager = bm;
    }

    @Override
    public S produce(CreationalContext<S> ctx) {
	Bean<?> listenerRegBean = beanManager.resolve(beanManager.getBeans(ListenerSetRegistry.class));
	ListenerSetRegistry registry = (ListenerSetRegistry) beanManager.getReference(listenerRegBean, ListenerSetRegistry.class, beanManager.createCreationalContext(listenerRegBean));
	Class<? extends ListenerInterface>[] value = annotation.value();
	if (value == null || value.length == 0) {
	    throw new DefinitionException("@Listeners annotation must define one value at least");
	}
	for (int i = 0; i < value.length; i++) {
	    Class<? extends ListenerInterface> clazz = value[i];
	    if (EntityOperationListenerInterface.class.isAssignableFrom(clazz)) {
		registry.getO(clazz);
	    }
	    if (EntitySelectionListenerInterface.class.isAssignableFrom(clazz)) {
		registry.getO(clazz);
	    }
	    
	}
	return (L) 
	return null;
    }

    @Override
    public void dispose(S instance) {
	// TODO Auto-generated method stub
	
    }

    @Override
    public Set<InjectionPoint> getInjectionPoints() {
	// TODO Auto-generated method stub
	return null;
    }

}
