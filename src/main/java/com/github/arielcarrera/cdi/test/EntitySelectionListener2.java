package com.github.arielcarrera.cdi.test;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EntitySelectionListener2 implements EntitySelectionListenerInterface {

    private int i = 0;
    
    @Override
    public int getId() {
	return 2002;
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
