package com.github.arielcarrera.cdi.test.config;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.WithAnnotations;

import com.github.arielcarrera.cdi.test.EntityOperationListenerInterface;
import com.github.arielcarrera.cdi.test.EntitySelectionListenerInterface;
import com.github.arielcarrera.cdi.test.ListenerInterface;

@Slf4j
public class ListenerRegistryExtension implements Extension {

    Set<Class<? extends EntityOperationListenerInterface>> operationListeners = new HashSet<>();

    Set<Class<? extends EntitySelectionListenerInterface>> selectionListeners = new HashSet<>();

    // 1
    void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager beanManager) {
	log.info("beforeBeanDiscovery");
    }

    // 2
    @SuppressWarnings("unchecked")
    void processAnnotatedType(@Observes @WithAnnotations({ EntityOperationListener.class }) ProcessAnnotatedType<?> pat,
	    BeanManager beanManager) {
	log.info("processAnnotatedType:" + pat.getAnnotatedType().toString());
	if (pat.getAnnotatedType().getAnnotation(EntityOperationListener.class) != null) {
	    log.error("anotado con @EntityOperationListener");
	    if (!operationListeners.contains(pat.getAnnotatedType().getJavaClass())) {
		if (EntityOperationListenerInterface.class.isAssignableFrom(pat.getAnnotatedType().getJavaClass())) {
		    synchronized (operationListeners) {
			operationListeners.add((Class<? extends EntityOperationListenerInterface>) pat
				.getAnnotatedType().getJavaClass());
		    }
		}
	    }
	}

    }

    @SuppressWarnings("unchecked")
    void processAnnotatedType2(
	    @Observes @WithAnnotations({ EntitySelectionListener.class }) ProcessAnnotatedType<?> pat,
	    BeanManager beanManager) {
	log.info("processAnnotatedType2:" + pat.getAnnotatedType().toString());
	if (pat.getAnnotatedType().getAnnotation(EntitySelectionListener.class) != null) {
	    log.error("anotado con @EntitySelectionListener");
	    if (!selectionListeners.contains(pat.getAnnotatedType().getJavaClass())) {
		if (EntitySelectionListenerInterface.class.isAssignableFrom(pat.getAnnotatedType().getJavaClass())) {
		    synchronized (selectionListeners) {
			selectionListeners.add((Class<? extends EntitySelectionListenerInterface>) pat
				.getAnnotatedType().getJavaClass());
		    }
		}
	    }
	}
    }

    // 3
    void afterTypeDiscovery(@Observes AfterTypeDiscovery event, BeanManager beanManager) {
	log.info("afterTypeDiscovery");
    }

    // 4
    <T, X> void processInjectionPoint(@Observes ProcessInjectionPoint<T, X> pip, BeanManager beanManager) {
	log.info("processInjectionPoint:" + pip.getInjectionPoint().toString());
    }

    // 5
    <T> void processInjectionTarget(@Observes final ProcessInjectionTarget<T> pit, BeanManager beanManager) {
	log.info("processInjectionTarget:" + pit.getInjectionTarget().toString());
    }

    // 6
    <T> void processBeanAttributes(@Observes ProcessBeanAttributes<T> pba, BeanManager beanManager) {
	log.info("processBeanAttributes:" + pba.getBeanAttributes().toString());
    }

    // 7
    <X> void processBean(@Observes final ProcessBean<X> pb, BeanManager beanManager) {
	log.info("ProcessBean:" + pb.getBean().getBeanClass().getSimpleName());
    }

    // 8
    <T, X> void processProducer(@Observes final ProcessProducer<T, X> pp, BeanManager beanManager) {
	log.info("ProcessProducer:" + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
    }

    // 8 (para ListenerSet
//    <X extends ListenerSet> void processProducer2(@Observes final ProcessProducer<?, X> pp, BeanManager beanManager) {
//	log.info("ProcessProducer2:" + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
//	String name = pp.getAnnotatedMember().getAnnotation(Metric.class).name();
//	pp.setProducer(new ListenerSetProducer<>(pp.getProducer(), name, beanManager));
//    }

    // 9
    <T, X> void processObserverMethod(@Observes ProcessObserverMethod<T, X> pom, BeanManager beanManager) {
	log.info("processObserverMethod:" + pom.getObserverMethod().toString());
    }

    // 10
    void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager beanManager) {
	log.info("afterBeanDiscovery");
    }

    // 11
    void afterDeploymentValidation(@Observes final AfterDeploymentValidation adb, BeanManager beanManager) {
	log.info("AfterDeploymentValidation");
    }

    // 12
    void beforeShutdown(@Observes final BeforeShutdown bs, BeanManager beanManager) {
	log.info("BeforeShutdown");
    }

//    void vetoListenerRegistry(@Observes ProcessAnnotatedType<?> pat) {
//	if (ListenerRegistry.class.isAssignableFrom(pat.getAnnotatedType().getJavaClass())) {
//	    pat.veto();
//	}
//    }

//    @SuppressWarnings("unchecked")
//    <X> void processBean(@Observes ProcessBean<X> processBean) {
//	Bean<X> bean = processBean.getBean();
//	for (Type type : bean.getTypes()) {
//	    // Check if the bean is a FooBaseInterface.
//	    if (type instanceof Class<?> && ListenerInterface.class.isAssignableFrom((Class<?>) type)) {
//		Set<Annotation> qualifiers = bean.getQualifiers();
//		for (Annotation annotation : qualifiers) {
//		    if (Listener.class == annotation.annotationType()) {
//			Class<? extends ListenerInterface> targetClass = ((Listener) annotation).value();
//			if (targetClass == null) {
//			    throw new RuntimeException("Error inicializando aplicacion, la anotacion @"
//				    + Listener.class.getSimpleName() + " debe contener un valor distinto de null");
//			}
//			ListenerRegistryBeanProducer.INSTANCE.put(ListenerRegistry.calculateKey(targetClass), processBean.);
//		    }
//		}
//	    }
//	}
//    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {
	log.info("finished the scanning process");
    }

    @SuppressWarnings("UnusedDeclaration")
    private <X extends ListenerInterface> void processProducer(@Observes final ProcessProducer<?, X> pp) {
	log.info("PROCESS PRODUCER!!!!" + pp.toString());
    }

}