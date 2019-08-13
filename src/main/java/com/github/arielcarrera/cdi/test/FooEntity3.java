package com.github.arielcarrera.cdi.test;

import lombok.Data;

import com.github.arielcarrera.cdi.test.config.Listener;

/**
 * Simple bean for testing purpose
 * @author Ariel Carrera
 *
 */
@Listener(FooEntity3.class)
@Data
public class FooEntity3 implements FooInterface {

	private int id = 3;

}