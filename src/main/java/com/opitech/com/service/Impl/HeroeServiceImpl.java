/**
 * 
 */
package com.opitech.com.service.Impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.opitech.com.Dto.HeroeDto;
import com.opitech.com.entity.Heroe;
import com.opitech.com.exception.HeroAlreadyExistsException;
import com.opitech.com.exception.HeroNotFoundException;
import com.opitech.com.repository.HeroeRepository;
import com.opitech.com.service.HeroeService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

/**
 * Servicio que maneja la lógica de negocio para héroes. Realiza operaciones de
 * creación, actualización y búsqueda.
 */
@Service
@Slf4j
public class HeroeServiceImpl implements HeroeService {

	private final HeroeRepository repository;

	private final ModelMapper mapper;

	private int page = 0;
	private int size = 10;
	private String[] sort = { "id", "asc" };

	private static final int create = 1;
	private static final int update = 2;

	public HeroeServiceImpl(HeroeRepository repository, ModelMapper mapper) {
		this.repository = repository;
		this.mapper = mapper;
	}

	/**
	 * Gets the all heroes.
	 *
	 * @return the all heroes
	 */
	@Override
	public Page<HeroeDto> getAllHeroes() {
		Sort.Direction direction = Sort.Direction.fromString(sort[1]);
		Sort sorting = Sort.by(direction, sort[0]);
		Pageable pageable = PageRequest.of(page, size, sorting);
		return repository.findAll(pageable).map(hero -> mapper.map(hero, HeroeDto.class));
	}

	/**
	 * Gets the heroe by id.
	 *
	 * @param id the id
	 * @return the heroe by id
	 */
	@Override
	public HeroeDto getHeroeById(Long id) {
		Heroe heroe = this.findById(id);
		return mapper.map(heroe, HeroeDto.class);
	}

	/**
	 * Creates the heroe.
	 *
	 * @param dto the dto
	 * @return the heroe dto
	 */
	@Override
	@Transactional
	@CacheEvict(value = "heroes", allEntries = true)
	public HeroeDto createHeroe(HeroeDto dto) {
		try {
			Heroe entity = mapper.map(updateDate(dto, create), Heroe.class);
			Heroe saved = this.saveHeroe(entity);
			;
			return mapper.map(saved, HeroeDto.class);
		} catch (DataIntegrityViolationException ex) {
			log.warn("Hero already exists with name={}", dto.getName(), ex);
			throw new HeroAlreadyExistsException(dto.getName());
		}
	}

	/**
	 * Update date.
	 *
	 * @param dto    the dto
	 * @param status the status
	 * @return the heroe dto
	 */
	private HeroeDto updateDate(HeroeDto dto, int status) {
		LocalDate today = LocalDate.now();
		switch (status) {
		case 1: {
			dto.setCreatedAt(today);
			break;
		}
		case 2: {			
			if (Objects.isNull(dto.getCreatedAt())) {
				throw new IllegalArgumentException("Unexpected value: created date " );
			}
			dto.setUpdatedAt(today);
			break;
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + status);
		}

		return dto;
	}

	/**
	 * Search heroes by name.
	 *
	 * @param name the name
	 * @return the list
	 */
	@Override
	public List<HeroeDto> searchHeroesByName(String name) {
		List<Heroe> lst = repository.searchHeroesByName(name);
		if (lst.isEmpty()) {
			throw new HeroNotFoundException(name);
		}
		return lst.stream().map(hero -> mapper.map(hero, HeroeDto.class)).toList();
	}

	/**
	 * Delete heroe.
	 *
	 * @param id the id
	 */
	@Override
	@Transactional
	@CacheEvict(value = "heroes", key = "#id")
	public void deleteHeroe(Long id) {
		Heroe heroe = this.findById(id);
		repository.delete(heroe);

	}

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the heroe
	 */
	private Heroe findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
	}

	/**
	 * Update heroe.
	 *
	 * @param id  the id
	 * @param dto the dto
	 * @return the heroe dto
	 */
	@Override
	@Transactional
	@CachePut(value = "heroes", key = "#id")
	public HeroeDto updateHeroe(Long id, HeroeDto dtoR) {

		Heroe heroe = this.findById(id);
		HeroeDto dto = this.updateDate(dtoR, update);
		heroe.setName(dto.getName());
		heroe.setNickName(dto.getNickName());
		heroe.setUniverse(dto.getUniverse());
		heroe.setPowerLevel(dto.getPowerLevel());
		heroe.setActive(dto.getActive());
		heroe.setUpdatedAt(dto.getUpdatedAt());
		Heroe saved = this.saveHeroe(heroe);
		
		return mapper.map(saved, HeroeDto.class);
	}

	/**
	 * Save heroe.
	 *
	 * @param heroe the heroe
	 * @return the heroe
	 */
	private Heroe saveHeroe(Heroe heroe) {
		return repository.save(heroe);
	}

}
