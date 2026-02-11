/**
 * 
 */
package com.opitech.com.util;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author psych
 *
 */
@Schema(description = "Universo del heroe")
public enum UniverseEnum {
	
	MARVEL, DC, OTHER;
	
	  @JsonCreator
	    public static UniverseEnum from(String value) {
	        return Arrays.stream(values())
	                .filter(v -> v.name().equalsIgnoreCase(value))
	                .findFirst()
	                .orElseThrow(() ->
	                        new IllegalArgumentException("Invalid HeroType: " + value));
	    }

}
