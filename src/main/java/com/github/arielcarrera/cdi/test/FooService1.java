package com.github.arielcarrera.cdi.test;

import lombok.Data;

import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.ListenerSet;
import com.github.arielcarrera.cdi.test.config.Listeners;

@Listeners({EntityOperationListener1.class})
@Data
public class FooService1 implements EntityOperationListenerInterface {

	private int id = 1;
	
	@Inject
	private ListenerSet opListeners;
	
//	@Inject @EntityOperationListener
//	private EntityOperationListenerInterface listener;
	
	
//	@Inject @EntitySelectionListener
//	private ListenerSet selListeners;

}