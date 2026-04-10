package com.concessionaria.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class VeiculoResponse {
    private Long id;
    private String modelo;
    private String marca;
    private String placa;
    private int ano;
    private Double valor;
}
