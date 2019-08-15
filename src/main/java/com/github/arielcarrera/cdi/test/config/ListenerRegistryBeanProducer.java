package com.github.arielcarrera.cdi.test.config;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

public class ListenerRegistryBeanProducer {

    public final static ListenerSetRegistry INSTANCE = new ListenerSetRegistry();
    
    @Produces
    @ApplicationScoped
    public ListenerSetRegistry getRegistry() {
	return INSTANCE;
    }
    
}
