package com.concessionaria.service;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.entity.Veiculo;
import com.concessionaria.exception.RecursoNaoEncontradoException;
import com.concessionaria.exception.RegraDeNegocioException;
import com.concessionaria.mapper.VeiculoMapper;
import com.concessionaria.repository.VeiculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {
    private static final Logger log = LoggerFactory.getLogger(VeiculoService.class);
    private final VeiculoRepository repository;
    private final VeiculoMapper mapper;


    public VeiculoService(VeiculoRepository repository, VeiculoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public VeiculoResponse criar(VeiculoRequest request) {
        log.info("Iniciando criar veiculo");
        log.info("VeiculoRequest: {}", request);
        if(repository.existsByPlaca(request.getPlaca())){
            throw new RegraDeNegocioException("Placa já existe no sistema");
        }
        Veiculo veiculo = mapper.toEntity(request);
        log.info("Veiculo: {}", veiculo);
        Veiculo salvo = repository.save(veiculo);
        log.info("Salvo: {}", salvo);
        return mapper.toResponse(salvo);
    }

    public VeiculoResponse buscarPorId(Long id) {
        log.info("Iniciando buscar por id");
        Veiculo veiculo = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado"));
        log.info("veiculo: {}", veiculo);
        VeiculoResponse veiculoResponse = mapper.toResponse(veiculo);
        log.info("veiculoResponse: {}", veiculoResponse);
        return veiculoResponse;
    }

    public VeiculoResponse atualizar(Long id, VeiculoRequest request) {
        log.info("Iniciando atualizar veiculo");
        Veiculo existente = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado"));
        Optional<Veiculo> veiculoComMesmaPlaca = repository.findByPlaca(request.getPlaca());

        veiculoComMesmaPlaca.ifPresent(veiculo -> {
            if (!veiculo.getId().equals(id)) {
                throw new RegraDeNegocioException("Placa já existe no sistema");
            }
        });
        log.info("existente: {}", existente);
        existente.setModelo(request.getModelo());
        existente.setMarca(request.getMarca());
        existente.setAno(request.getAno());
        existente.setPlaca(request.getPlaca());
        existente.setValor(request.getValor());
        log.info("existente: {}", existente);

        Veiculo atualizado = repository.save(existente);
        log.info("atualizado: {}", atualizado);

        VeiculoResponse veiculoResponse = mapper.toResponse(atualizado);
        log.info("veiculoResponse: {}", veiculoResponse);
        return veiculoResponse;
    }

    public void deletar(Long id) {
        log.info("Iniciando deletar veiculo");
        Veiculo veiculo = repository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("Veículo não encontrado"));
        log.info("veiculo: {}", veiculo);

        repository.delete(veiculo);
    }

    public List<VeiculoResponse> buscarComFiltros(
            String marca,
            String modelo,
            Integer ano,
            Double valorMin,
            Double valorMax,
            String placa) {
        log.info("Iniciando buscar com Filtros");
        List<Veiculo> resultado = repository.buscarComFiltros(marca, modelo, ano, valorMin, valorMax, placa);
        log.info("resultado: {}", resultado);
        List<VeiculoResponse> response = mapper.toResponseList(resultado);
        log.info("response: {}", response);
        return response;
    }
}
