package com.github.arielcarrera.cdi.test;

import com.github.arielcarrera.cdi.test.config.EntitySelectionListener;

@EntitySelectionListener
public class EntitySelectionListener1 implements EntitySelectionListenerInterface {

    @Override
    public int getId() {
	return 2001;
    }

}
