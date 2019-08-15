package com.github.arielcarrera.cdi.test.config;

import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.Producer;

import com.github.arielcarrera.cdi.test.ListenerInterface;

class ListenerSetProducer<S extends ListenerSet, L extends ListenerInterface> implements Producer<S> {
    final Producer<S> decorate;
    final Class<L> targetClass;
    final BeanManager beanManager;

    ListenerSetProducer(Producer<S> decorate, Class<L> targetClass, BeanManager bm) {
	this.decorate = decorate;
	this.targetClass = targetClass;
	this.beanManager = bm;
    }

    @Override
    public S produce(CreationalContext<S> ctx) {
//	Bean<?> listenerRegBean = beanManager.resolve(beanManager.getBeans(ListenerSetRegistry.class));
//	ListenerSetRegistry registry = (ListenerSetRegistry) beanManager.getReference(listenerRegBean, ListenerSetRegistry.class, beanManager.createCreationalContext(listenerRegBean));
//	return (L) registry.get(targetClass);
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
