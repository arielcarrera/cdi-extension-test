package com.github.arielcarrera.cdi.test;

import com.github.arielcarrera.cdi.test.config.EntityOperationListener;

@EntityOperationListener
public class Listener1 implements EntityOperationListenerInterface {

    @Override
    public int getId() {
	return 1001;
    }

}
