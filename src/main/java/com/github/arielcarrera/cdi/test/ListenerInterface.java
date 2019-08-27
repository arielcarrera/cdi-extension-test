package com.github.arielcarrera.cdi.test;

import javax.enterprise.inject.Vetoed;

@Vetoed
public interface ListenerInterface {

    void setI(int i);

    int getI();
    
}
