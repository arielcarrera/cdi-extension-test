package com.github.arielcarrera.cdi.test.config;

import lombok.extern.slf4j.Slf4j;

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

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Slf4j
public class ListenerRegistryExtension implements Extension {

	// 1
	void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd, BeanManager beanManager) {
		log.info("beforeBeanDiscovery");
	}

	// 2
	void processListenersAnnotatedType(@Observes @WithAnnotations({ Listeners.class }) ProcessAnnotatedType<?> pat,
			BeanManager beanManager) {
		log.info("processListenersAnnotatedType:" + pat.getAnnotatedType().toString());
	}

	// 3
	void afterTypeDiscovery(@Observes AfterTypeDiscovery event, BeanManager beanManager) {
		log.info("afterTypeDiscovery");
	}

	// 4
	<T extends ListenerInterface, X> void processInjectionPoint(@Observes ProcessInjectionPoint<T, X> pip,
			BeanManager beanManager) {
		log.info("processInjectionPoint:" + pip.getInjectionPoint().toString());
	}

	// 5
	<T> void processInjectionTarget(@Observes final ProcessInjectionTarget<T> pit, BeanManager beanManager) {
		log.info("processInjectionTarget:" + pit.getInjectionTarget().toString());
	}

	// 6
	<T> void processBeanAttributes(@Observes ProcessBeanAttributes<T> pba, BeanManager beanManager) {
		log.info("processBeanAttributes:" + pba.getBeanAttributes().toString());
		// aqui se puede interrumpir la definicion de los atributos del bean
	}

	// 7
	<X> void processBean(@Observes final ProcessBean<X> pb, BeanManager beanManager) {
		log.info("ProcessBean:" + pb.getBean().getBeanClass().getSimpleName());
		// Se puede interceptar aqui cuando se procesa el bean ListenerSet

	}

	// 8
	<T, X> void processProducer(@Observes final ProcessProducer<T, X> pp, BeanManager beanManager) {
		log.info("ProcessProducer:" + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
		// se puede interceptar aqui ListenerSet y pisar el producer para que obtenga
		// del contexto las instancias segun la clase
	}

	<T, X extends ListenerSet> void processProducer2(@Observes final ProcessProducer<T, X> pp, BeanManager beanManager) {
		log.info("ProcessProducer: " + pp.getAnnotatedMember().getDeclaringType().getJavaClass().getSimpleName());
	}

	// 9
	<T, X> void processObserverMethod(@Observes ProcessObserverMethod<T, X> pom, BeanManager beanManager) {
		log.info("processObserverMethod:" + pom.getObserverMethod().toString());
	}

	// 10
	void afterBeanDiscovery(@Observes AfterBeanDiscovery abd, BeanManager beanManager) {
		log.info("finished the scanning process");
		log.info("afterBeanDiscovery");
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

}