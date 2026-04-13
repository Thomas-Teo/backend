package com.concessionaria.service;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.entity.Veiculo;
import com.concessionaria.mapper.VeiculoMapper;
import com.concessionaria.repository.VeiculoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VeiculoServiceTest {

    @Mock
    private VeiculoRepository repository;

    @Mock
    private VeiculoMapper mapper;

    @InjectMocks
    private VeiculoService service;

    private VeiculoRequest request;
    private Veiculo veiculo;
    private Veiculo veiculoSalvo;
    private VeiculoResponse response;

    @BeforeEach
    void setUp() {
        request = new VeiculoRequest();
        request.setModelo("Civic");
        request.setMarca("Honda");
        request.setAno(2020);
        request.setPlaca("ABC1234");
        request.setValor(85000.0);

        veiculo = new Veiculo();
        veiculo.setModelo("Civic");
        veiculo.setMarca("Honda");
        veiculo.setAno(2020);
        veiculo.setPlaca("ABC1234");
        veiculo.setValor(85000.0);

        veiculoSalvo = new Veiculo();
        veiculoSalvo.setId(1L);
        veiculoSalvo.setModelo("Civic");
        veiculoSalvo.setMarca("Honda");
        veiculoSalvo.setAno(2020);
        veiculoSalvo.setPlaca("ABC1234");
        veiculoSalvo.setValor(85000.0);

        response = new VeiculoResponse();
        response.setId(1L);
        response.setModelo("Civic");
        response.setMarca("Honda");
        response.setAno(2020);
        response.setPlaca("ABC1234");
        response.setValor(85000.0);
    }

    @Test
    @DisplayName("Deve Criar Veiculo com Sucesso")
    void deveCriarVeiculoComSucesso() {
        when(mapper.toEntity(request)).thenReturn(veiculo);
        when(repository.save(veiculo)).thenReturn(veiculoSalvo);
        when(mapper.toResponse(veiculoSalvo)).thenReturn(response);

        VeiculoResponse resultado = service.criar(request);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Civic", resultado.getModelo());

        verify(mapper).toEntity(request);
        verify(repository).save(veiculo);
        verify(mapper).toResponse(veiculoSalvo);
    }

    @Test
    @DisplayName("Deve Buscar Veiculo por Id com Sucesso")
    void deveBuscarVeiculoPorIdComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(veiculoSalvo));
        when(mapper.toResponse(veiculoSalvo)).thenReturn(response);

        VeiculoResponse resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Honda", resultado.getMarca());

        verify(repository).findById(1L);
        verify(mapper).toResponse(veiculoSalvo);
    }

    @Test
    @DisplayName("Deve Lancar Excecao ao Buscar por Id Inexistente")
    void deveLancarExcecaoAoBuscarPorIdInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.buscarPorId(1L));

        assertEquals("Veículo não encontrado", exception.getMessage());
        verify(repository).findById(1L);
        verify(mapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Deve Atualizar Veiculo com Sucesso")
    void deveAtualizarVeiculoComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(veiculoSalvo));
        when(repository.save(any(Veiculo.class))).thenReturn(veiculoSalvo);
        when(mapper.toResponse(any(Veiculo.class))).thenReturn(response);

        VeiculoResponse resultado = service.atualizar(1L, request);

        assertNotNull(resultado);
        assertEquals("Civic", resultado.getModelo());

        ArgumentCaptor<Veiculo> captor = ArgumentCaptor.forClass(Veiculo.class);
        verify(repository).save(captor.capture());

        Veiculo veiculoAtualizado = captor.getValue();
        assertEquals("Civic", veiculoAtualizado.getModelo());
        assertEquals("Honda", veiculoAtualizado.getMarca());
        assertEquals(2020, veiculoAtualizado.getAno());
        assertEquals("ABC1234", veiculoAtualizado.getPlaca());
        assertEquals(85000.0, veiculoAtualizado.getValor());

        verify(repository).findById(1L);
        verify(mapper).toResponse(any(Veiculo.class));
    }

    @Test
    @DisplayName("Deve Lancar Excecao ao Atualizar Veiculo Inexistente")
    void deveLancarExcecaoAoAtualizarVeiculoInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.atualizar(1L, request));

        assertEquals("Veículo não encontrado", exception.getMessage());
        verify(repository).findById(1L);
        verify(repository, never()).save(any());
        verify(mapper, never()).toResponse(any());
    }

    @Test
    @DisplayName("Deve Deletar Veiculo com Sucesso")
    void deveDeletarVeiculoComSucesso() {
        when(repository.findById(1L)).thenReturn(Optional.of(veiculoSalvo));

        service.deletar(1L);

        verify(repository).findById(1L);
        verify(repository).delete(veiculoSalvo);
    }

    @Test
    @DisplayName("Deve Lancar Excecao ao Deletar Veiculo Inexistente")
    void deveLancarExcecaoAoDeletarVeiculoInexistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> service.deletar(1L));

        assertEquals("Veículo não encontrado", exception.getMessage());
        verify(repository).findById(1L);
        verify(repository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve Buscar Veiculos com Filtros")
    void deveBuscarVeiculosComFiltros() {
        List<Veiculo> veiculos = List.of(veiculoSalvo);
        List<VeiculoResponse> responses = List.of(response);

        when(repository.buscarComFiltros("Honda", "Civic", 2020, 80000.0, 90000.0, "ABC1234"))
                .thenReturn(veiculos);
        when(mapper.toResponseList(veiculos)).thenReturn(responses);

        List<VeiculoResponse> resultado = service.buscarComFiltros(
                "Honda", "Civic", 2020, 80000.0, 90000.0, "ABC1234"
        );

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Civic", resultado.get(0).getModelo());

        verify(repository).buscarComFiltros("Honda", "Civic", 2020, 80000.0, 90000.0, "ABC1234");
        verify(mapper).toResponseList(veiculos);
    }
}