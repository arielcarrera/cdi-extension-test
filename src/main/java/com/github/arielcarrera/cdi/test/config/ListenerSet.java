package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.github.arielcarrera.cdi.test.ListenerInterface;

public class ListenerSet implements Serializable {

    private static final long serialVersionUID = -3115166732077559919L;

    private final Set<ListenerInterface> listeners = new HashSet<ListenerInterface>();

    public Set<ListenerInterface> get(){
	return listeners;
    }
    
    public void add(ListenerInterface li) {
	if (!listeners.contains(li)) {
	    synchronized (this) {
		listeners.add(li);
	    }
	}
    }
    
}
