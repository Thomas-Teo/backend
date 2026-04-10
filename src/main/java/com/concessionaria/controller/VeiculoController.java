package com.concessionaria.controller;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.service.VeiculoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    private final VeiculoService service;


    public VeiculoController(VeiculoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<VeiculoResponse> criar(
            @RequestBody @Valid VeiculoRequest request) {

        VeiculoResponse response = service.criar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VeiculoResponse> buscarPorId(@PathVariable Long id) {
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

        List<VeiculoResponse> response = service.buscarComFiltros(marca, modelo, ano, valorMin, valorMax, placa);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VeiculoResponse> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid VeiculoRequest request) {

        return ResponseEntity.ok(service.atualizar(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
