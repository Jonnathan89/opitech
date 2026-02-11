package com.opitech.com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import com.opitech.com.Dto.HeroeDto;
import com.opitech.com.controller.HeroeController;
import com.opitech.com.exception.HeroAlreadyExistsException;
import com.opitech.com.exception.HeroNotFoundException;
import com.opitech.com.service.HeroeService;
import com.opitech.com.util.UniverseEnum;

class HeroeControllerTest {

	private HeroeService service;
	private HeroeController controller;

	@BeforeEach
	void setUp() {
		// Crear el mock del service
		service = mock(HeroeService.class);

		// Inyectarlo en el controller mediante constructor
		controller = new HeroeController(service);
	}

	@Test
	@DisplayName("Actualizar héroe correctamente")
	void updateHeroe_Success() {
		// Arrange
		Long heroeId = 1L;

		HeroeDto dto = new HeroeDto();
		dto.setId(heroeId);
		dto.setName("Spiderman");
		dto.setNickName("Peter");
		dto.setUniverse(UniverseEnum.MARVEL);
		dto.setPowerLevel(85);
		dto.setActive(true);
		dto.setCreatedAt(LocalDate.of(2023, 1, 1));
		dto.setUpdatedAt(LocalDate.now());

		when(service.updateHeroe(eq(heroeId), any(HeroeDto.class))).thenReturn(dto);
		ResponseEntity<HeroeDto> response = controller.updateHeroe(heroeId, dto);
		assertNotNull(response);
		assertEquals(200, response.getStatusCodeValue());
		assertNotNull(response.getBody());

		HeroeDto body = response.getBody();
		assertEquals(heroeId, body.getId());
		assertEquals("Spiderman", body.getName());
		assertEquals("Peter", body.getNickName());
		assertEquals(UniverseEnum.MARVEL, body.getUniverse());
		assertEquals(85, body.getPowerLevel());
		assertTrue(body.getActive());
		assertEquals(LocalDate.of(2023, 1, 1), body.getCreatedAt());
		assertEquals(LocalDate.now(), body.getUpdatedAt());

		verify(service, times(1)).updateHeroe(eq(heroeId), any(HeroeDto.class));
	}

	@Test
	@DisplayName("Actualizar héroe inexistente devuelve 404 Not Found")
	void updateHeroe_NotFound() {

		Long heroeId = 999L;
		HeroeDto dto = new HeroeDto();
		dto.setId(heroeId);
		dto.setName("Héroe Fantasma");

		when(service.updateHeroe(eq(heroeId), any(HeroeDto.class))).thenThrow(new HeroNotFoundException(dto.getName()));

		HeroNotFoundException thrown = assertThrows(HeroNotFoundException.class,
				() -> controller.updateHeroe(heroeId, dto));

		assertEquals("Hero not found with name: " + dto.getName(), thrown.getMessage());

		verify(service, times(1)).updateHeroe(eq(heroeId), any(HeroeDto.class));
	}

	@Test
	@DisplayName("Actualizar héroe con nombre duplicado devuelve 409 Conflict")
	void updateHeroe_NameConflict() {

		Long heroeId = 1L;
		HeroeDto dto = new HeroeDto();
		dto.setId(heroeId);
		dto.setName("Spiderman");

		when(service.updateHeroe(eq(heroeId), any(HeroeDto.class))).thenThrow(new HeroAlreadyExistsException("1"));

		HeroAlreadyExistsException thrown = assertThrows(HeroAlreadyExistsException.class,
				() -> controller.updateHeroe(heroeId, dto));

		assertEquals("Hero already exists with name: 1", thrown.getMessage());

		verify(service, times(1)).updateHeroe(eq(heroeId), any(HeroeDto.class));
	}

	@Test
	@DisplayName("Actualizar héroe genera error inesperado devuelve 500 Internal Server Error")
	void updateHeroe_InternalServerError() {

		Long heroeId = 1L;
		HeroeDto dto = new HeroeDto();
		dto.setId(heroeId);
		dto.setName("Héroe Crash");

		when(service.updateHeroe(eq(heroeId), any(HeroeDto.class)))
				.thenThrow(new RuntimeException("Error inesperado del servidor"));

		RuntimeException thrown = assertThrows(RuntimeException.class, () -> controller.updateHeroe(heroeId, dto));

		assertEquals("Error inesperado del servidor", thrown.getMessage());

		verify(service, times(1)).updateHeroe(eq(heroeId), any(HeroeDto.class));
	}

}
