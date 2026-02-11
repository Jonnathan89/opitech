/**
 * 
 */
package com.opitech.com.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.opitech.com.Dto.HeroeDto;
import com.opitech.com.service.HeroeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

/**
 * Controlador REST para la gestión de héroes.
 * Permite crear, actualizar, eliminar y consultar héroes por id y todos.
 */
@RestController
@RequestMapping(value = "/heroes")
public class HeroeController {

	private final HeroeService service;

	public HeroeController(HeroeService service) {
		this.service = service;
	}

	@Operation(summary = "Buscar todos los Heroes por paginación ", description = "Obtiene todos los heroes con paginanción")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
			@ApiResponse(responseCode = "500", description = "Error inesperado") })
	@GetMapping
	public ResponseEntity<Page<HeroeDto>> getAllHeroes() {
		return ResponseEntity.ok(service.getAllHeroes());
	}

	@Operation(summary = "Buscar el heroe por id ", description = "Obtiene el heroe por id")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Heroe obtenida correctamente"),
			@ApiResponse(responseCode = "500", description = "Error inesperado"),
			@ApiResponse(responseCode = "404", description = "Nopt found"),
			@ApiResponse(responseCode = "201", description = "valor encontrado") })
	@GetMapping("/{id}")
	public ResponseEntity<HeroeDto> getHeroeById(@PathVariable("id") Long id) {
		return ResponseEntity.ok(service.getHeroeById(id));
	}

	@Operation(summary = "crea heroe con datos enviados ", description = "Ocrea heroe con datos enviados")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Heroe obtenida correctamente"),
			@ApiResponse(responseCode = "500", description = "Error inesperado"),
			@ApiResponse(responseCode = "409", description = "The name value is already in the DB "),
			@ApiResponse(responseCode = "201", description = "valor encontrado") })
	@PostMapping
	public ResponseEntity<Void> createHeroe(@RequestBody HeroeDto bodyHe) {
		HeroeDto heroCreated = service.createHeroe(bodyHe);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(heroCreated.getId()).toUri();
		return ResponseEntity.created(location).build();
	}

	@Operation(summary = "Buscar Heroes por nombre ", description = "Obtiene todos los heroes por nombre")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Lista obtenida correctamente"),
			@ApiResponse(responseCode = "500", description = "Error inesperado"),
			@ApiResponse(responseCode = "404", description = "Not found "),
			@ApiResponse(responseCode = "201", description = "valor encontrado") })
	@GetMapping("/search")
	public ResponseEntity<List<HeroeDto>> searchHeroesByName(@RequestParam("name") String name) {
		return ResponseEntity.ok(service.searchHeroesByName(name));
	}

	@Operation(summary = "eliminar heroe por id", description = "Elimina el heroe")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Objeto eliminado correctamente"),
			@ApiResponse(responseCode = "500", description = "Error inesperado"),
			@ApiResponse(responseCode = "404", description = "Not found ") })
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteHeroe(@PathVariable("id") Long id) {
		service.deleteHeroe(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Actualizar héroe", description = "Actualiza un héroe existente por su ID")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Objeto actualizado correctamente"),
			@ApiResponse(responseCode = "404", description = "Héroe no encontrado"),
			@ApiResponse(responseCode = "409", description = "El valor del nombre ya existe en la base de datos"),
			@ApiResponse(responseCode = "500", description = "Error inesperado del servidor") })
	@PutMapping("/{id}")
	public ResponseEntity<HeroeDto> updateHeroe(@PathVariable("id") Long id, @RequestBody HeroeDto dto) {
		HeroeDto heroe = service.updateHeroe(id, dto);
		return ResponseEntity.ok(heroe);
	}

}
