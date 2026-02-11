/**
 * 
 */
package com.opitech.com.exception;

/**
 * Excepción lanzada cuando un héroe no se encuentra en la base de datos.
 */
public class HeroNotFoundException extends RuntimeException {


	private static final long serialVersionUID = 1L;


	public HeroNotFoundException(Long id) {
		super("Hero not found with id: " + id);
	}
	
	
	public HeroNotFoundException(String name) {
		super("Hero not found with name: " + name);
	}
}
