/**
 * 
 */
package com.opitech.com.entity;

import java.time.LocalDate;

import com.opitech.com.util.UniverseEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * @author psych
 *
 */
@Entity
@Table(name = "heroe")
@Data
public class Heroe {

	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(nullable = false, length = 100, unique = true)
	    private String name;

	    @Column( name ="nick_name" , length = 100)
	    private String nickName;
	    
	    @Column(nullable = false, length = 20)
	    private UniverseEnum universe;

	    @Column(name = "power_level", nullable = false)
	    private Integer powerLevel;

	    @Column(nullable = false)
	    private Boolean active = true;

	    @Column(name = "created_at", nullable = false, updatable = false)
	    private LocalDate createdAt;

	    @Column(name = "updated_at")
	    private LocalDate updatedAt;
	
	
}
