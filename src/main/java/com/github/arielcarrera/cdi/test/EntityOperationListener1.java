package com.github.arielcarrera.cdi.test;

public class EntityOperationListener1 implements EntityOperationListenerInterface {

    private int i = 0;
    
    @Override
    public int getId() {
	return 1001;
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
