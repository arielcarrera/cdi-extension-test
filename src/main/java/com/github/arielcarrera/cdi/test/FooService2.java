package com.github.arielcarrera.cdi.test;

import lombok.Data;

import javax.enterprise.context.ApplicationScoped;

import com.github.arielcarrera.cdi.test.config.Listeners;

/**
 * Simple service for testing purpose
 * @author Ariel Carrera
 *
 */
@Listeners({EntitySelectionListener1.class})
@ApplicationScoped
@Data
public class FooService2 {

	private int id = 2;
	
//	@Inject @EntityOperationListener
//	private ListenerSet opListeners;
	
//	@Inject @EntitySelectionListener
//	private ListenerSet selListeners;
}