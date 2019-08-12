package com.github.arielcarrera.cdi.test.config;

import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.AfterBeanDiscovery;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeforeBeanDiscovery;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessBean;

import com.github.arielcarrera.cdi.test.FooInterface;
import com.github.arielcarrera.cdi.test.MyAnnotation;

@Slf4j
public class ListenerRegistryExtension implements Extension {

    Map<String, Bean<FooInterface>> registro = new HashMap<String, Bean<FooInterface>>();

    public Bean<FooInterface> getRegistro(String key) {
	return registro.get(key);
    }

    public synchronized void putIfAbsent(String key, Bean<FooInterface> value) {
	registro.putIfAbsent(key, value);
    }

    public void beforeBeanDiscovery(@Observes BeforeBeanDiscovery bbd) {

	log.info("beginning the scanning process");

    }

//    public <T> void processAnnotatedType(@Observes @WithAnnotations(MyAnnotation.class) ProcessAnnotatedType<T> pat) {
//
//	log.info("scanning type: " + pat.getAnnotatedType().getJavaClass().getName());
//	if (FooInterface.class.isAssignableFrom(pat.getAnnotatedType().getJavaClass())) {
//	    log.info("Registrando instancia de FooInterface: " + pat.getAnnotatedType().getJavaClass().getName());
//	    MyAnnotation ann = pat.getAnnotatedType().getAnnotation(MyAnnotation.class);
//	    String key = ann.value();
//	    if (key == null || key.trim().isEmpty()) {
//		throw new RuntimeException("Error inicializando aplicacion, la anotacion @" + MyAnnotation.class.getSimpleName() + " debe contener un valor distinto de vacio");
//	    }
//	    put(key, (Class<FooInterface>) pat.getAnnotatedType().getJavaClass());
//	} else {
//	    log.warn("Se ha detectado clase anotada con " +  MyAnnotation.class.getName() + " pero la misma no implementa un Listener valido");
//	}
//
//    }

    @SuppressWarnings("unchecked")
    <X> void processBean(@Observes ProcessBean<X> processBean) {
	Bean<X> bean = processBean.getBean();
	for (Type type : bean.getTypes()) {
	    // Check if the bean is a FooInterface.
	    if (type instanceof Class<?> && FooInterface.class.isAssignableFrom((Class<?>) type)) {
		Set<Annotation> qualifiers = bean.getQualifiers();
		for (Annotation annotation : qualifiers) {
		    if (MyAnnotation.class == annotation.annotationType()) {
			String key = ((MyAnnotation) annotation).value();
			if (key == null || key.trim().isEmpty()) {
			    throw new RuntimeException("Error inicializando aplicacion, la anotacion @"
				    + MyAnnotation.class.getSimpleName() + " debe contener un valor distinto de vacio");
			}
			putIfAbsent(key, (Bean<FooInterface>) bean);
		    }
		}
	    }
	}
    }

    public void afterBeanDiscovery(@Observes AfterBeanDiscovery abd) {

	log.info("finished the scanning process");

    }

//    void afterBeanDiscovery(@Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager) {
//
//	for (Entry<Class<?>, Set<Annotation>> entry : getRepositoryTypes()) {
//
//	    Class<?> repositoryType = entry.getKey();
//	    Set<Annotation> qualifiers = entry.getValue();
//
//	    // Create the bean representing the repository.
//	    CdiRepositoryBean<?> repositoryBean = createRepositoryBean(repositoryType, qualifiers, beanManager);
//	    LOGGER.info("Registering bean for '{}' with qualifiers {}.", repositoryType.getName(), qualifiers);
//
//	    // Register the bean to the extension and the container.
//	    registerBean(repositoryBean);
//	    afterBeanDiscovery.addBean(repositoryBean);
//	}
//    }
//
//    private <T> FooInterfaceBean<T> createRepositoryBean(Class<T> repositoryType, Set<Annotation> qualifiers,
//	    BeanManager beanManager) {
//
//	// Determine the entity manager bean which matches the qualifiers of the
//	// repository.
//	Bean<EntityManager> entityManagerBean = entityManagers.get(qualifiers);
//	// MODIFICADO para agregar entitymanager
//	Bean<EntityManager> entityManagerBeanCreation = null;
//	// Se busca un entitymanager dependiente para la creacion
//	for (Set<Annotation> s : entityManagers.keySet()) {
//	    if (s.toString().contains(RepositoryCreation.class.getName())) {
//		entityManagerBeanCreation = entityManagers.get(s);
//		break;
//	    }
//	}
//
//	if (entityManagerBean == null) {
//	    throw new UnsatisfiedResolutionException(
//		    String.format("Unable to resolve a bean for '%s' with qualifiers %s.",
//			    EntityManager.class.getName(), qualifiers));
//	}
//
//	if (entityManagerBeanCreation == null) {
//	    entityManagerBeanCreation = entityManagerBean;
//	}
//
//	// Construct and return the repository bean.
//	return new JpaRepositoryBean<T>(beanManager, entityManagerBean, entityManagerBeanCreation, qualifiers,
//		repositoryType, Optional.of(getCustomImplementationDetector()));
//    }

}