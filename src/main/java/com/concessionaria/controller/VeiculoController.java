package com.concessionaria.controller;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.service.VeiculoService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private static final Logger log = LoggerFactory.getLogger(VeiculoController.class);
    private final VeiculoService service;


    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> criar(
            @RequestBody @Valid VeiculoRequest request) {
        log.info("Entrou endpoint Post");
        log.info("VeiculoRequest: {}", request);
        VeiculoResponse response = service.criar(request);
        log.info("VeiculoResponse: {}", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
        log.info("Entrou endpoint Get");
        log.info("Id: {}", id);
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<VeiculoResponse>> buscar(
            @RequestParam(required = false) String marca,
            @RequestParam(required = false) String modelo,
            @RequestParam(required = false) Integer ano,
            @RequestParam(required = false) Double valorMin,
            @RequestParam(required = false) Double valorMax,
            @RequestParam(required = false) String placa) {
        log.info("Entrou endpoint Get com filtros");
        log.info("Marca: {}", marca);
        log.info("Modelo: {}", modelo);
        log.info("Ano: {}", ano);
        log.info("Valor minimo: {}", valorMin);
        log.info("Valor maximo: {}", valorMax);
        log.info("Placa: {}", placa);
        List<VeiculoResponse> response = service.buscarComFiltros(marca, modelo, ano, valorMin, valorMax, placa);
        log.info("VeiculoResponse: {}", response);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid VeiculoRequest request) {
        log.info("Entrou endpoint Put");
        log.info("VeiculoRequest: {}", request);
        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        log.info("Entrou endpoint Delete");
        log.info("Id: {}", id);
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
