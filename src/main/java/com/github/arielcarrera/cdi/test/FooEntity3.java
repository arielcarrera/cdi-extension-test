package com.github.arielcarrera.cdi.test;

import lombok.Data;

/**
 * Simple bean for testing purpose
 * @author Ariel Carrera
 *
 */
@MyAnnotation("FooEntityListener3")
@Data
public class FooEntity3 implements FooInterface {

	private int id = 3;

}