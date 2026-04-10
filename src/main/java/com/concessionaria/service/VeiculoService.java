package com.concessionaria.service;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.entity.Veiculo;
import com.concessionaria.mapper.VeiculoMapper;
import com.concessionaria.repository.VeiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService {

   private final VeiculoRepository repository;
   private final VeiculoMapper mapper;


    public VeiculoService(VeiculoRepository repository, VeiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public VeiculoResponse criar(VeiculoRequest request) {
        Veiculo veiculo = mapper.toEntity(request);
        Veiculo salvo = repository.save(veiculo);
        return mapper.toResponse(salvo);
    }

    public VeiculoResponse buscarPorId(Long id) {
        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        return mapper.toResponse(veiculo);
    }

    public List<VeiculoResponse> listar() {
        List<Veiculo> veiculos = repository.findAll();

        return mapper.toResponseList(veiculos);
    }

    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        Veiculo existente = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        existente.setModelo(request.getModelo());
        existente.setMarca(request.getMarca());
        existente.setAno(request.getAno());
        existente.setPlaca(request.getPlaca());
        existente.setValor(request.getValor());

        Veiculo atualizado = repository.save(existente);

        return mapper.toResponse(atualizado);
    }

    public void deletar(Long id) {
        Veiculo veiculo = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));

        repository.delete(veiculo);
    }

    public List<VeiculoResponse> buscarComFiltros(
            String marca,
            String modelo,
            Integer ano,
            Double valorMin,
            Double valorMax,
            String placa) {

        List<Veiculo> resultado = repository.buscarComFiltros(
                marca, modelo, ano, valorMin, valorMax, placa
        );
        List<VeiculoResponse> response = mapper.toResponseList(resultado);
        return response;
    }
}
