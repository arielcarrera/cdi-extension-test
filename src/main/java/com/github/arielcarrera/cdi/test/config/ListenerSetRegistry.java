package com.github.arielcarrera.cdi.test.config;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;

import com.github.arielcarrera.cdi.test.EntityOperationListenerInterface;
import com.github.arielcarrera.cdi.test.EntitySelectionListenerInterface;

@ApplicationScoped
public class ListenerSetRegistry implements Serializable {

	private static final long serialVersionUID = -3115166732077559919L;

	private final Set<Class<? extends EntityOperationListenerInterface>> operationListeners = new HashSet<>();

	private final Set<Class<? extends EntitySelectionListenerInterface>> selectionListeners = new HashSet<>();

	public Set<Class<? extends EntityOperationListenerInterface>> getOperationListener() {
		return operationListeners;
	}

	public void addOperationListener(Class<? extends EntityOperationListenerInterface> li) {
		if (!operationListeners.contains(li)) {
			synchronized (operationListeners) {
				operationListeners.add(li);
			}
		}
	}

	public Set<Class<? extends EntitySelectionListenerInterface>> getSelectionListener() {
		return selectionListeners;
	}

	public void addSelectionListener(Class<? extends EntitySelectionListenerInterface> li) {
		if (!selectionListeners.contains(li)) {
			synchronized (selectionListeners) {
				selectionListeners.add(li);
			}
		}
	}
}
