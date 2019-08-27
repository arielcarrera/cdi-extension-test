package com.github.arielcarrera.cdi.test;

public class EntitySelectionListener1 implements EntitySelectionListenerInterface {

    private int i = 0;
    
    @Override
    public int getId() {
	return 2001;
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
