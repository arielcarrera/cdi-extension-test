package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.enterprise.inject.Vetoed;

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Vetoed
public class ListenerClassRegistry implements Serializable {

	private static final long serialVersionUID = -3115166732077559919L;

//	private final Set<Class<? extends ListenerInterface>> listenerRegistry = new HashSet<>();
	
	private final Map<Class<?>, Set<Class<? extends ListenerInterface>>> listenersAnnotationMap = new HashMap<>();

//	public boolean contains(Class<? extends ListenerInterface> clazz) {
//		return listenerRegistry.contains(clazz);
//	}
//
//	public void add(Class<? extends ListenerInterface> clazz) {
//		if (!listenerRegistry.contains(clazz)) {
//			synchronized(listenerRegistry) {
//				listenerRegistry.add(clazz);
//			}
//		}
//	}
	
	public Set<Class<? extends ListenerInterface>> getListenersForClass(Class<?> clazz) {
		return listenersAnnotationMap.get(clazz);
	}

	public void putListenersForClassIfAbsent(Class<?> clazz, Set<Class<? extends ListenerInterface>> listeners) {
		if (!listenersAnnotationMap.containsKey(clazz)) {
			synchronized(listenersAnnotationMap) {
				listenersAnnotationMap.put(clazz, listeners);
			}
		}
	}
}
