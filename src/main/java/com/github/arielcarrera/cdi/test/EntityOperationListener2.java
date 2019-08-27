package com.github.arielcarrera.cdi.test;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EntityOperationListener2 implements EntityOperationListenerInterface {

    private int i = 0;
    
    @Override
    public int getId() {
	return 1002;
    }
    
    @Override
    public void setI(int i) {
	this.i = i;
    }
    
    @Override
    public int getI() {
	return this.i;
    }

}
