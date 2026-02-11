package com.opitech.com.Dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.opitech.com.util.UniverseEnum;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO que representa un héroe con sus atributos principales.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO que representa un heroe")
public class HeroeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "ID del heroe", example = "1")
    private Long id;

    @Schema(description = "Nombre del heroe", example = "Superman")
    @NotBlank(message = "El parámetro 'name' no puede estar vacío")
    @Size(min = 2, message = "El parámetro 'name' debe tener al menos 2 caracteres")
    @Pattern(regexp = "^(\\S.*\\S|\\S)$", message = "El parámetro 'name' no puede tener espacios al inicio ni al final")
    private String name;

    @Schema(description = "Apodo del heroe", example = "Clark")
    private String nickName;

    @Schema(description = "Universo al que pertenece el heroe", example = "DC")
    private UniverseEnum universe;

    @Schema(description = "Nivel de poder del heroe", example = "85")
    @Min(value = 1, message = "El powerLevel debe ser al menos 1")
    @Max(value = 100, message = "El powerLevel no puede ser mayor a 100")
    private Integer powerLevel;

    @Schema(description = "Indica si el heroe está activo", example = "true")
    private Boolean active = true;

    @Schema(description = "Fecha de creación del heroe", example = "2026-02-11")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;

    @Schema(description = "Fecha de actualización del heroe", example = "2026-02-11")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate updatedAt;

}
