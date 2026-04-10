package com.concessionaria.service;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.mapper.VeiculoMapper;
import com.concessionaria.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

   private final VeiculoRepository repository;

    public VeiculoService(VeiculoRepository repository) {
        this.repository = repository;
    }

    public VeiculoResponse criar(VeiculoRequest request) {
        return null;
    }

    public VeiculoResponse buscarPorId(Long id) {
        return null;
    }

    public List<VeiculoResponse> listar() {
        return null;
    }

    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        return null;
    }

    public void deletar(Long id) {
    }

    public List<VeiculoResponse> buscarComFiltros(
            String marca,
            String modelo,
            Integer ano,
            Double valorMin,
            Double valorMax,
            String placa) {

        return null;
    }
}
