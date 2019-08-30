package com.github.arielcarrera.cdi.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Iterator;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.CreationException;
import javax.enterprise.inject.Instance;
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
	return ShrinkWrap.create(JavaArchive.class).addPackages(true, ListenerInterface.class.getPackage())
		.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
		// .addAsResource(new
		// File("src/main/resources/META-INF/beans.xml"),"/META-INF/beans.xml")
		.addAsResource(new File("src/main/resources/META-INF/services/javax.enterprise.inject.spi.Extension"),
			"/META-INF/services/javax.enterprise.inject.spi.Extension");
    }
//    @Inject
//    EntityOperationListener1 listener;

    @Inject
    FooService1 service1; // Dependent

    @Inject
    FooService2 service2; // Application

    @Inject
    FooService3 service3; // Request

    @Inject @Any
    Instance<FooService4BadReference> service4BRInstance; // Dependent Self Reference - ERROR - CICLE REFERENCE WITH DEPENDENT PSEUDO SCOPE NOT ALLOWED (https://stackoverflow.com/a/29903516/2493852)

    @Inject
    FooService5 service5; // Application with Other Service/Listener Dependent Reference

    @Inject
    FooService6 service6; // Application Self Reference

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

    @Test(expected = CreationException.class)
    public void testService4_CircularReference_NotSupported() {
	assertTrue(service4BRInstance.isResolvable());
	//EXCEPTION DUE TO CIRCULAR DEPENDENT REFERENCE (NORMAL SCOPE IS REQUIRED)
	service4BRInstance.select().get();
    }

    @Test
    public void testService5() {
	assertTrue(service5.getId() == 5);
	assertEquals(2, service5.getListeners().getOperationListenersSize());
	Iterator<ListenerInterface> iterator = service5.getListeners().getOperationListeners().iterator();
	Class<? extends ListenerInterface> class1 = iterator.next().getClass();
	Class<? extends ListenerInterface> class2 = iterator.next().getClass();
	assertTrue((EntityOperationListener1.class.isAssignableFrom(class1) && FooService1.class.isAssignableFrom(class2)
		)||(EntityOperationListener1.class.isAssignableFrom(class2) && FooService1.class.isAssignableFrom(class1)));
    }

    @Test
    public void testService6() {
	assertTrue(service6.getId() == 6);
	assertEquals(2, service6.getListeners().getOperationListenersSize());
	Iterator<ListenerInterface> iterator = service6.getListeners().getOperationListeners().iterator();
	Class<? extends ListenerInterface> class1 = iterator.next().getClass();
	Class<? extends ListenerInterface> class2 = iterator.next().getClass();
	assertTrue((EntityOperationListener1.class.isAssignableFrom(class1) && FooService6.class.isAssignableFrom(class2)
		)||(EntityOperationListener1.class.isAssignableFrom(class2) && FooService6.class.isAssignableFrom(class1)));
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

    private static boolean dependentServiceReferenceApplicationScopedBeanFlag = false;

    @Test
    public void testDependentServiceReferenceListenerApplicationBean_parte1() {
	testSelfReferenceListenerApplicationBean();
    }

    @Test
    public void testDependentServiceReferenceListenerApplicationBean_parte2() {
	testSelfReferenceListenerApplicationBean();
    }

    public void testDependentServiceReferenceListenerApplicationBean() {
	Iterator<ListenerInterface> iterator = service5.getListeners().getOperationListeners().iterator();
	
	ListenerInterface servList = iterator.next();
	ListenerInterface servList2 = iterator.next();
	EntityOperationListener1 eol1 = null;
	FooService1 fs1 = null;
	if (EntityOperationListener1.class.isAssignableFrom(servList.getClass())) {
	    eol1 = (EntityOperationListener1) servList;
	    fs1 = (FooService1) servList2;
	} else {
	    eol1 = (EntityOperationListener1) servList2;
	    fs1 = (FooService1) servList;
	}
	if (!dependentServiceReferenceApplicationScopedBeanFlag) {
	    assertEquals(0, eol1.getI());
	    assertEquals(0, fs1.getI());
	    eol1.setI(100);
	    fs1.setI(200);
	    dependentServiceReferenceApplicationScopedBeanFlag = true;
	} else {
	    assertEquals(100, eol1.getI());
	    assertEquals(200, fs1.getI());
	}
    }

    private static boolean selfReferenceApplicationScopedBeanFlag = false;

    @Test
    public void testSelfReferenceListenerApplicationBean_parte1() {
	testSelfReferenceListenerApplicationBean();
    }

    @Test
    public void testSelfReferenceListenerApplicationBean_parte2() {
	testSelfReferenceListenerApplicationBean();
    }

    public void testSelfReferenceListenerApplicationBean() {
	Iterator<ListenerInterface> iterator = service6.getListeners().getOperationListeners().iterator();
	ListenerInterface servList = iterator.next();
	ListenerInterface servList2 = iterator.next();
	EntityOperationListener1 eol1 = null;
	FooService6 fs6 = null;
	if (EntityOperationListener1.class.isAssignableFrom(servList.getClass())) {
	    eol1 = (EntityOperationListener1) servList;
	    fs6 = (FooService6) servList2;
	} else {
	    eol1 = (EntityOperationListener1) servList2;
	    fs6 = (FooService6) servList;
	}
	if (!selfReferenceApplicationScopedBeanFlag) {
	    assertEquals(0, eol1.getI());
	    assertEquals(0, fs6.getI());
	    eol1.setI(100);
	    fs6.setI(200);
	    service6.setI(service6.getI() + 100);
	    selfReferenceApplicationScopedBeanFlag = true;
	} else {
	    assertEquals(100, eol1.getI());
	    assertEquals(300, fs6.getI());
	}
    }

}