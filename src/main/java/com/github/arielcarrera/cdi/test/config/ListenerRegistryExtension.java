package com.github.arielcarrera.cdi.test.config;

import java.util.Arrays;
import java.util.HashSet;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.AfterDeploymentValidation;
import javax.enterprise.inject.spi.AfterTypeDiscovery;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.BeforeShutdown;
import javax.enterprise.inject.spi.DefinitionException;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.enterprise.inject.spi.InjectionTarget;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.ProcessBean;
import javax.enterprise.inject.spi.ProcessBeanAttributes;
import javax.enterprise.inject.spi.ProcessInjectionPoint;
import javax.enterprise.inject.spi.ProcessInjectionTarget;
import javax.enterprise.inject.spi.ProcessObserverMethod;
import javax.enterprise.inject.spi.ProcessProducer;
import javax.enterprise.inject.spi.WithAnnotations;

import com.github.arielcarrera.cdi.test.ListenerInterface;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ListenerRegistryExtension implements Extension {

	private final ListenerClassRegistry classRegistry = new ListenerClassRegistry();

	// 1
	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager beanManager) {
		log.info("beforeBeanDiscovery");
	}

	// 2
	void processListenersAnnotatedType(@Observes @WithAnnotations({ Listeners.class }) ProcessAnnotatedType<?> pat,
			BeanManager beanManager) {
		log.info("processListenersAnnotatedType:" + pat.getAnnotatedType().toString());
		Listeners annotation = pat.getAnnotatedType().getAnnotation(Listeners.class);
		if (annotation.value() == null || annotation.value().length < 1) {
			throw new DefinitionException("Se debe definir al menos un valor en la anotacion @Listeners");
		} else {
			Class<? extends ListenerInterface>[] value = annotation.value();
			classRegistry.putListenersForClassIfAbsent(pat.getAnnotatedType().getJavaClass(),
					new HashSet<>(Arrays.asList(value)));
		}
	}

	// 3
	void afterTypeDiscovery(@Observes AfterTypeDiscovery event, BeanManager beanManager) {
		log.info("afterTypeDiscovery");
	}

	// 4
	<T extends ListenerInterface, X> void processInjectionPoint(@Observes ProcessInjectionPoint<T, X> pip,
			BeanManager beanManager) {
		log.info("processInjectionPoint:" + pip.getInjectionPoint().toString());
		InjectionPoint ip = pip.getInjectionPoint();
		Class<?> declaringClass = ip.getMember().getDeclaringClass();
		log.info("InjectionPoint -> declaring Class = " + declaringClass.getName());
		// aca puedo obtener donde se intenta inyectar un ListenerSet
	}

	// 5
	<T> void processInjectionTarget(@Observes final ProcessInjectionTarget<T> pit, BeanManager beanManager) {
		log.info("processInjectionTarget:" + pit.getInjectionTarget().toString());
		InjectionTarget it = pit.getInjectionTarget();
		if (pit.getAnnotatedType().getAnnotation(Listeners.class) != null) {
			log.info("InjectionTarget -> class with Listeners " + pit.getAnnotatedType().getJavaClass().getName());
			// aca puedo evaluar donde una clase esta anotada con Listeners y tiene
			// inyeccion o es destino de la misma
		}
	}

	// 6
	<T> void processBeanAttributes(@Observes ProcessBeanAttributes<T> pba, BeanManager beanManager) {
		log.info("processBeanAttributes:" + pba.getBeanAttributes().toString());
		pba.getBeanAttributes();
		// aqui se puede interrumpir la definicion de los atributos del bean
	}

	// 7
	<X> void processBean(@Observes final ProcessBean<X> pb, BeanManager beanManager) {
		log.info("ProcessBean:" + pb.getBean().getBeanClass().getSimpleName());
		pb.getBean();
		// Se puede interceptar aqui cuando se procesa el bean ListenerSet

	}

	// 8
	<T, X> void processProducer(@Observes final ProcessProducer<T, X> pp, BeanManager beanManager) {
		log.info("ProcessProducer:" + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
		pp.getAnnotatedMember();
		// se puede interceptar aqui ListenerSet y pisar el producer para que obtenga
		// del contexto las instancias segun la clase
	}

	<T, X extends ListenerSet> void processProducer2(@Observes final ProcessProducer<T, X> pp, BeanManager beanManager) {
		log.info("ProcessProducer: " + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
		//pp.setProducer(new ListenerSetProducer<>(pp.getProducer(), pp.getAnnotatedMember().getAnnotation(Listeners.class), beanManager));
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
		log.info("finished the scanning process");
		log.info("afterBeanDiscovery");
		Iterable<AnnotatedType<Listeners>> annotatedTypes = abd.getAnnotatedTypes(Listeners.class);
		// se puede aqui inspeccionar las clases anotadas por alguna clase para agregar
		// una nueva definicion
	}

	// 11
	void afterDeploymentValidation(@Observes final AfterDeploymentValidation adv, BeanManager beanManager) {
		log.info("AfterDeploymentValidation");
		// se puede indicar aqui si hubo un problema despues de la validacion del
		// despliegue
	}

	// 12
	void beforeShutdown(@Observes final BeforeShutdown bs, BeanManager beanManager) {
		log.info("BeforeShutdown");
		// antes de apagar
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

}