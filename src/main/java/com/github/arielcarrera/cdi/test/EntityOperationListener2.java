package com.github.arielcarrera.cdi.test;

import com.github.arielcarrera.cdi.test.config.EntityOperationListener;

@EntityOperationListener
public class EntityOperationListener2 implements EntityOperationListenerInterface {

    @Override
    public int getId() {
	return 1002;
    }

}
