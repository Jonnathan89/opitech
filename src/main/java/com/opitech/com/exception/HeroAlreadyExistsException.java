/**
 * 
 */
package com.opitech.com.exception;

import lombok.Data;

/**
 * Excepción lanzada cuando un héroe ya se encuentra en la base de datos.
 */
@Data
public class HeroAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final String heroName;

	public HeroAlreadyExistsException(String heroName) {
		super("Hero already exists with name: " + heroName);
		this.heroName = heroName;
	}

}
