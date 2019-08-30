package com.github.arielcarrera.cdi.test.config;

import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.ResolutionException;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.InjectionPoint;

import com.github.arielcarrera.cdi.test.EntityOperationListenerInterface;
import com.github.arielcarrera.cdi.test.EntitySelectionListenerInterface;
import com.github.arielcarrera.cdi.test.ListenerInterface;

public class ListenerSetProducer {

    public final static ListenerRegistry REGISTRY = new ListenerRegistry();

    @Produces
    @Dependent
    public ListenerSet getListenerSet(BeanManager bm, InjectionPoint ip) {
	String clazzName = ip.getBean().getBeanClass().getName();
	Set<Class<? extends ListenerInterface>> listeners = REGISTRY.getListeners(clazzName);

	if (listeners == null) {
	    Listeners l = ip.getBean().getBeanClass().getAnnotation(Listeners.class);
	    listeners = REGISTRY.setListeners(clazzName, l);
	}
	
	if (listeners.isEmpty()) {
	    return new ListenerSet();
	}

	ListenerSet result = new ListenerSet();
	for (Class<? extends ListenerInterface> li : listeners) {
	    Set<Bean<?>> beans = bm.getBeans(li, Default.Literal.INSTANCE);
	    if (beans == null || beans.size() < 1) {
		throw new ResolutionException("No es posible resolver bean para clase: " + li.getName());
	    }
	    Bean<? extends Object> bean = bm.resolve(beans);
	    ListenerInterface reference = (ListenerInterface) bm.getReference(bean, li,
		    bm.createCreationalContext(bean));
	    if (EntityOperationListenerInterface.class.isAssignableFrom(li)) {
		result.addOperationListener((EntityOperationListenerInterface) reference);
	    }
	    if (EntitySelectionListenerInterface.class.isAssignableFrom(li)) {
		result.addSelectionListener((EntitySelectionListenerInterface) reference);
	    }
	}
	return result;
    }

}
