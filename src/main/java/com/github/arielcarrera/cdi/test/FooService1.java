package com.github.arielcarrera.cdi.test;

import lombok.Data;

import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.EntityOperationListener;
import com.github.arielcarrera.cdi.test.config.Listeners;

@Listeners({Listener1.class})
@Data
public class FooService1 implements EntityOperationListenerInterface {

	private int id = 1;
	
//	@Inject @EntityOperationListener @Listeners(Listener1.class)
//	private ListenerSet opListeners;
	
	@Inject @EntityOperationListener
	private EntityOperationListenerInterface listener;
	
	
//	@Inject @EntitySelectionListener
//	private ListenerSet selListeners;

}