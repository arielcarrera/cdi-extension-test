package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Dependent
public class ListenerSet implements Serializable {

    private static final long serialVersionUID = -3115166732077559919L;

    private final Set<ListenerInterface> operationListeners = new HashSet<ListenerInterface>();
    
    private final Set<ListenerInterface> selectionListeners = new HashSet<ListenerInterface>();

    public Set<ListenerInterface> getOperationListener(){
	return operationListeners;
    }
    
    public void addOperationListener(ListenerInterface li) {
	if (!operationListeners.contains(li)) {
	    synchronized (operationListeners) {
		operationListeners.add(li);
	    }
	}
    }
    
    
    public Set<ListenerInterface> getSelectionListener(){
	return selectionListeners;
    }
    
    public void addSelectionListener(ListenerInterface li) {
	if (!selectionListeners.contains(li)) {
	    synchronized (selectionListeners) {
		selectionListeners.add(li);
	    }
	}
    }
    
}
