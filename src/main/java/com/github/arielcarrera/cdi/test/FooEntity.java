package com.github.arielcarrera.cdi.test;

import lombok.Data;

import com.github.arielcarrera.cdi.test.config.Listener;

/**
 * Simple bean for testing purpose
 * @author Ariel Carrera
 *
 */
@Listener(FooEntity.class)
@Data
public class FooEntity implements FooInterface {

	private int id = 1;

}