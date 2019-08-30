package com.github.arielcarrera.cdi.test;

import lombok.Data;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.ListenerSet;
import com.github.arielcarrera.cdi.test.config.Listeners;

/**
 * Simple service for testing purpose
 * @author Ariel Carrera
 *
 */
@Listeners({EntityOperationListener2.class, EntitySelectionListener2.class})
@RequestScoped
@Data
public class FooService3 {

	private int id = 3;
	
	@Inject
	private ListenerSet listeners;
	
//	@Inject
//	private ListenerSet opListeners;	
	
//	@Inject @EntitySelectionListener
//	private ListenerSet selListeners;
}