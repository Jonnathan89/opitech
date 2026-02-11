/**
 * 
 */
package com.opitech.com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.opitech.com.entity.Heroe;

/**
 * @author psych
 *
 */
@Repository
public interface  HeroeRepository extends JpaRepository<Heroe, Long> {

	
	@Query("SELECT h FROM Heroe h WHERE LOWER(h.name) LIKE LOWER(CONCAT('%', :name, '%')) ORDER BY h.name ASC")
    List<Heroe> searchHeroesByName(@Param("name") String name);
	
}
