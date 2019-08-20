package com.github.arielcarrera.cdi.test;

import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.ListenerSet;
import com.github.arielcarrera.cdi.test.config.Listeners;

import lombok.Data;

@Listeners({EntityOperationListener1.class})
@Data
public class FooService1 implements EntityOperationListenerInterface {

	private int id = 1;
	
	@Inject
	private ListenerSet opListeners;
	
//	@Inject
//	private EntityOperationListenerInterface listener;
	
	
//	@Inject
//	private ListenerSet selListeners;

}