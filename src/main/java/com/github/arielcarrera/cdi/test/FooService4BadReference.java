package com.github.arielcarrera.cdi.test;

import lombok.Data;

import javax.inject.Inject;

import com.github.arielcarrera.cdi.test.config.ListenerSet;
import com.github.arielcarrera.cdi.test.config.Listeners;

@Listeners({EntityOperationListener1.class, FooService4BadReference.class})
@Data
public class FooService4BadReference implements EntityOperationListenerInterface {
    	
    	private int i = 0;
    
	private int id = 4;
	
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