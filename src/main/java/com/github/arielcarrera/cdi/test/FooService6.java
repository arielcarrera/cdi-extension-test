package com.github.arielcarrera.cdi.test;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.ListenerSet;
import com.github.arielcarrera.cdi.test.config.Listeners;

import lombok.Data;

@ApplicationScoped
@Listeners({EntityOperationListener1.class, FooService6.class})
@Data
public class FooService6 implements EntityOperationListenerInterface {
    	
    	private int i = 0;
    
	private int id = 6;
	
	@Inject
	private ListenerSet listeners;

	@Override
	public void setI(int i) {
	    this.i = i;
	}

	@Override
	public int getI() {
	    return this.i;
	}
	
//	@Inject
//	private EntityOperationListenerInterface listener;
	
	
//	@Inject
//	private ListenerSet selListeners;

}