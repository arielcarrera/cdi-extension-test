package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.inject.Vetoed;

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Vetoed
public class ListenerRegistry implements Serializable {

    private static final long serialVersionUID = -3115166732077559919L;

    private final Map<String, Set<Class<? extends ListenerInterface>>> registry = new ConcurrentHashMap<>();

    public Set<Class<? extends ListenerInterface>> getListeners(String clazz) {
	return registry.get(clazz);
    }

    public Set<Class<? extends ListenerInterface>>  setListeners(String clazz, Listeners listeners) {
	Set<Class<? extends ListenerInterface>> s = null;
	if (listeners == null || listeners.value() == null || listeners.value().length == 0) {
	    s = Collections.emptySet();
	    registry.putIfAbsent(clazz, s);
	} else {
	    Class<? extends ListenerInterface>[] value = listeners.value();
	    s = new HashSet<>();
	    for (int i = 0; i < value.length; i++) {
		s.add(value[i]);
	    }
	    registry.putIfAbsent(clazz, s);
	}
	return s;
    }

}
