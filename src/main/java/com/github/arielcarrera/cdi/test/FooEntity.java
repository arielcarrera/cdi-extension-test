package com.github.arielcarrera.cdi.test;

import lombok.Data;

/**
 * Simple bean for testing purpose
 * @author Ariel Carrera
 *
 */
@MyAnnotation("FooEntityListener")
@Data
public class FooEntity implements FooInterface {

	private int id = 1;

}