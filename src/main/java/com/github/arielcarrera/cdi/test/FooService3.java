package com.github.arielcarrera.cdi.test;

import javax.enterprise.context.RequestScoped;

import com.github.arielcarrera.cdi.test.config.Listeners;

/**
 * Simple service for testing purpose
 * @author Ariel Carrera
 *
 */
@Listeners({EntityOperationListener1.class, EntitySelectionListener1.class})
@RequestScoped
public class FooService3 {

	private int id = 3;
	
//	@Inject
//	private ListenerSet opListeners;	
	
//	@Inject @EntitySelectionListener
//	private ListenerSet selListeners;
}