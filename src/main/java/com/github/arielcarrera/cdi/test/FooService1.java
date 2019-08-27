package com.github.arielcarrera.cdi.test;

import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.ListenerSet;
import com.github.arielcarrera.cdi.test.config.Listeners;

import lombok.Data;

@Listeners({EntityOperationListener1.class})
@Data
public class FooService1 implements EntityOperationListenerInterface {
    	
    	private int i = 0;
    
	private int id = 1;
	
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