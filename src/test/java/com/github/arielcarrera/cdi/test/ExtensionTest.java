package com.github.arielcarrera.cdi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Instance;
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
public class ExtensionTest {

    @Rule
    public WeldInitiator weld = WeldInitiator.from(new Weld()).inject(this).build();

    @Inject @Any
    Instance<FooInterface> instance;
    
    @Test
    public void testFooEntityListener1() {
	assertEquals(instance.select(new MyAnnotation.Literal("FooEntityListener")).get().getId(), 1);
    }
    
    @Test
    public void testFooEntityListener2_default() {
	assertEquals(instance.select(Default.Literal.INSTANCE).get().getId(), 2);
    }
    
    @Test
    public void testFooEntityListener3() {
	assertEquals(instance.select(new MyAnnotation.Literal("FooEntityListener3")).get().getId(), 3);
    }
    
    @Test
    public void testFooEntityListener_notExists() {
	assertFalse(instance.select(new MyAnnotation.Literal("FooEntityListenerNotExists")).isResolvable());
    }
    
    @Test
    public void testFooEntityListener_duplicate() {
	assertTrue(instance.select(Any.Literal.INSTANCE).isAmbiguous());
    }

}