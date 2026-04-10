package com.concessionaria.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VeiculoRequest {
    @NotBlank(message = "Modelo é obrigatório")
    private String modelo;

    @NotBlank(message = "Marca é obrigatória")
    private String marca;

    @Min(value = 1900, message = "Ano inválido")
    private Integer ano;

    @NotBlank(message = "Placa é obrigatória")
    @Size(min = 7, max = 7, message = "Placa deve ter 7 caracteres")
    private String placa;

    @Positive(message = "Valor deve ser positivo")
    private Double valor;
}
