package com.github.arielcarrera.cdi.test.config;

import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
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

public class ListenerRegistryBeanProducer {

    public final static ListenerSetRegistry INSTANCE = new ListenerSetRegistry();
    
    @Produces
    @ApplicationScoped
    public ListenerSetRegistry getRegistry() {
	return INSTANCE;
    }
    
    @Produces
    @Dependent
    public ListenerSet getListenerSet(BeanManager bm, InjectionPoint ip) {
    	Listeners l = ip.getBean().getBeanClass().getAnnotation(Listeners.class);
    	ListenerSet result = new ListenerSet();
    	if(l.value() != null) {

    		for (Class<? extends ListenerInterface> li : l.value()) {
				Set<Bean<?>> beans = bm.getBeans(li, Default.Literal.INSTANCE);
				if (beans == null || beans.size() < 1) {
					throw new ResolutionException("No es posible resolver bean para clase: " +  li.getName());
				}
				Bean<? extends Object> bean = bm.resolve(beans);
				ListenerInterface reference = (ListenerInterface) bm.getReference(bean, li, bm.createCreationalContext(bean));
				if (EntityOperationListenerInterface.class.isAssignableFrom(li)) {
					result.addOperationListener((EntityOperationListenerInterface)reference);
				}
				if (EntitySelectionListenerInterface.class.isAssignableFrom(li)) {
					result.addSelectionListener((EntitySelectionListenerInterface)reference);
				}
			}
    	}
    	return result;
    }
    
}
