package com.github.arielcarrera.cdi.test;
import static org.junit.Assert.assertTrue;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.junit4.WeldInitiator;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test of CDI Extension
 * 
 * @author Ariel Carrera
 *
 */
public class ExtensionStandaloneTest {

    @Rule
    public WeldInitiator weld = WeldInitiator.from(new Weld()).activate(RequestScoped.class).inject(this).build();

    @Inject
    FooService1 service1;
    
    @Inject
    FooService2 service2;
    
    @Inject
    FooService3 service3;
    
    @Test
    public void test1() {
	assertTrue(service1.getId() == 1);
    }
    

}