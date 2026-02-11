/**
 * 
 */
package com.opitech.com.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.opitech.com.Dto.HeroeDto;

/**
 * @author psych
 *
 */

public interface HeroeService {

	Page<HeroeDto> getAllHeroes();

	HeroeDto getHeroeById(Long id);

	HeroeDto createHeroe(HeroeDto dto);

	List<HeroeDto> searchHeroesByName(String name);

	void deleteHeroe(Long id);

	HeroeDto updateHeroe(Long id, HeroeDto dto);

}
