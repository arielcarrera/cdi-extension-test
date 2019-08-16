package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.enterprise.inject.Vetoed;

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Vetoed
public class ListenerSetRegistry implements Serializable {

    private static final long serialVersionUID = -3115166732077559919L;

    private final Map<String, ListenerSet> listenerRegistry = new HashMap<String, ListenerSet>();

    public ListenerSet get(String key) {
	return listenerRegistry.get(key);
    }
    
    public ListenerSet get(Class<? extends ListenerInterface> clazz) {
	return listenerRegistry.get(calculateKey(clazz));
    }

    public synchronized void put(String key, ListenerInterface value) {
	if (!listenerRegistry.containsKey(key)) {
	    ListenerSet set = new ListenerSet();
	    set.addOperationListener(value);
	    listenerRegistry.put(key, set);
	} else {
	    ListenerSet set = listenerRegistry.get(key);
	    set.addOperationListener(value);
	}
    }
    
    public void put(Class<? extends ListenerInterface> clazz, ListenerInterface value) {
	String key = calculateKey(clazz);
	put(key, value);
    }
    
    public static String calculateKey(Class<? extends ListenerInterface> targetClass) {
	Class<?>[] interfaces = targetClass.getInterfaces();
	String s = Arrays.asList(interfaces).stream().map(c -> c.getName()).collect(Collectors.joining(":"));
	return s + ":" + targetClass.getName();
    }
}
