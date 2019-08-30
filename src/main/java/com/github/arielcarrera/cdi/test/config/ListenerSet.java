package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.inject.Vetoed;

import com.github.arielcarrera.cdi.test.ListenerInterface;

@Vetoed
public class ListenerSet implements Serializable {

    private static final long serialVersionUID = -3115166732077559919L;

    private final Set<ListenerInterface> operationListeners = new HashSet<ListenerInterface>();
    
    private final Set<ListenerInterface> selectionListeners = new HashSet<ListenerInterface>();

    public void addOperationListener(ListenerInterface li) {
	if (!operationListeners.contains(li)) {
	    synchronized (operationListeners) {
		operationListeners.add(li);
	    }
	}
    }
    public void addSelectionListener(ListenerInterface li) {
	if (!selectionListeners.contains(li)) {
	    synchronized (selectionListeners) {
		selectionListeners.add(li);
	    }
	}
    }
    
    public Set<ListenerInterface> getOperationListeners(){
	return operationListeners;
    }
    
    public Set<ListenerInterface> getSelectionListeners(){
	return selectionListeners;
    }
    
    public int getOperationListenersSize() {
	return operationListeners.size();
    }
    
    public int getSelectionListenersSize() {
	return selectionListeners.size();
    }
    
}
