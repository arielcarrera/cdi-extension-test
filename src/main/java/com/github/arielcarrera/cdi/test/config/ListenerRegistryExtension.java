package com.github.arielcarrera.cdi.test.config;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import com.github.arielcarrera.cdi.test.FooInterface;

@Slf4j
public class ListenerRegistryExtension implements Extension {

    private ListenerRegistry listenerRegistry = new ListenerRegistry();


    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {

	log.info("beginning the scanning process");

    }

//    void vetoListenerRegistry(@Observes ProcessAnnotatedType<?> pat) {
//	if (ListenerRegistry.class.isAssignableFrom(pat.getAnnotatedType().getJavaClass())) {
//	    pat.veto();
//	}
//    }
    
    
    @SuppressWarnings("unchecked")
    <X> void processBean(@Observes ProcessBean<X> processBean) {
	Bean<X> bean = processBean.getBean();
	for (Type type : bean.getTypes()) {
	    // Check if the bean is a FooInterface.
	    if (type instanceof Class<?> && FooInterface.class.isAssignableFrom((Class<?>) type)) {
		Set<Annotation> qualifiers = bean.getQualifiers();
		for (Annotation annotation : qualifiers) {
		    if (Listener.class == annotation.annotationType()) {
			Class<? extends FooInterface> key = ((Listener) annotation).value();
			if (key == null) {
			    throw new RuntimeException("Error inicializando aplicacion, la anotacion @"
				    + Listener.class.getSimpleName() + " debe contener un valor distinto de null");
			}
			listenerRegistry.putIfAbsent(key.getName(), (Bean<FooInterface>) bean);
		    }
		}
	    }
	}
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
	log.info("finished the scanning process");
	abd.addBean(new ListenerRegistryBean(listenerRegistry));
    }

}