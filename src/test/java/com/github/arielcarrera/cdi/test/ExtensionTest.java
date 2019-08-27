package com.github.arielcarrera.cdi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test of CDI Extension
 * 
 * @author Ariel Carrera
 *
 */
@RunWith(Arquillian.class)
public class ExtensionTest {

//    @Rule
//    public WeldInitiator weld = WeldInitiator.from(new Weld()).activate(RequestScoped.class).inject(this).build();
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addPackages(true, ListenerInterface.class.getPackage())
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
            //.addAsResource(new File("src/main/resources/META-INF/beans.xml"),"/META-INF/beans.xml")
            .addAsResource(new File("src/main/resources/META-INF/services/javax.enterprise.inject.spi.Extension"),"/META-INF/services/javax.enterprise.inject.spi.Extension");
    }
//    @Inject
//    EntityOperationListener1 listener;
    
    @Inject
    FooService1 service1; // Dependent

    @Inject
    FooService2 service2; // Application

    @Inject
    FooService3 service3; // Request

    @Test
    public void testService1() {
	assertTrue(service1.getId() == 1);
	assertEquals(1, service1.getListeners().getOperationListenersSize());
	assertEquals(0, service1.getListeners().getSelectionListenersSize());
	assertTrue(EntityOperationListener1.class
		.isAssignableFrom(service1.getListeners().getOperationListeners().iterator().next().getClass()));
    }

    @Test
    public void testService2() {
	assertTrue(service2.getId() == 2);
	assertEquals(0, service2.getListeners().getOperationListenersSize());
	assertEquals(1, service2.getListeners().getSelectionListenersSize());
	assertTrue(EntitySelectionListener1.class
		.isAssignableFrom(service2.getListeners().getSelectionListeners().iterator().next().getClass()));
    }

    @Test
    public void testService3() {
	assertTrue(service3.getId() == 3);
	assertEquals(1, service3.getListeners().getOperationListenersSize());
	assertEquals(1, service3.getListeners().getSelectionListenersSize());
	assertTrue(EntityOperationListener2.class
		.isAssignableFrom(service3.getListeners().getOperationListeners().iterator().next().getClass()));
	assertTrue(EntitySelectionListener2.class
		.isAssignableFrom(service3.getListeners().getSelectionListeners().iterator().next().getClass()));
    }

    private static boolean applicationScopedFlag = false;

    @Test
    public void testApplicationScoped_parte1() {
	testApplicationScoped();
    }
    
    @Test
    public void testApplicationScoped_parte2() {
	testApplicationScoped();
    }
    
    public void testApplicationScoped() {
	ListenerInterface servList = service3.getListeners().getOperationListeners().iterator().next();
	if (!applicationScopedFlag) {
	    assertEquals(0, servList.getI());
	    servList.setI(100);
	    applicationScopedFlag = true;
	} else {
	    assertEquals(100, servList.getI());
	}
    }

    private static boolean requestScopedFlag = false;
    
    @Test
    public void testRequestScoped_parte1() {
	testRequestScoped();
    }

    @Test
    public void testRequestScoped_parte2() {
	testRequestScoped();
    }
    
    public void testRequestScoped() {
	ListenerInterface servList = service3.getListeners().getSelectionListeners().iterator().next();
	if (!requestScopedFlag) {
	    assertEquals(0, servList.getI());
	    servList.setI(100);
	    requestScopedFlag = true;
	} else {
	    assertEquals(0, servList.getI());
	}
    }

    
    private static boolean dependentPseudoScopedDependentBeanFlag = false;
    
    @Test
    public void testDependentPseudoScopedDependentBean_parte1() {
	testDependentPseudoScopedDependentBean();
    }

    @Test
    public void testDependentPseudoScopedDependentBean_parte2() {
	testDependentPseudoScopedDependentBean();
    }

    public void testDependentPseudoScopedDependentBean() {
	ListenerInterface servList = service1.getListeners().getOperationListeners().iterator().next();
	if (!dependentPseudoScopedDependentBeanFlag) {
	    assertEquals(0, servList.getI());
	    servList.setI(100);
	    dependentPseudoScopedDependentBeanFlag = true;
	} else {
	    assertEquals(0, servList.getI());
	}
    }
    
    private static boolean dependentPseudoScopedApplicationBeanFlag = false;
    
    @Test
    public void testDependentPseudoScopedApplicationBean_parte1() {
	testDependentPseudoScopedApplicationBean();
    }

    @Test
    public void testDependentPseudoScopedApplicationBean_parte2() {
	testDependentPseudoScopedApplicationBean();
    }

    public void testDependentPseudoScopedApplicationBean() {
	ListenerInterface servList = service2.getListeners().getSelectionListeners().iterator().next();
	if (!dependentPseudoScopedApplicationBeanFlag) {
	    assertEquals(0, servList.getI());
	    servList.setI(100);
	    dependentPseudoScopedApplicationBeanFlag = true;
	} else {
	    assertEquals(100, servList.getI());
	}
    }
    
    @Test
    public void testSelfImplementListener() {
    }

}