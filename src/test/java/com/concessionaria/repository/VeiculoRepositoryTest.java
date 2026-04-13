package com.concessionaria.repository;

import com.concessionaria.entity.Veiculo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class VeiculoRepositoryTest {

    @Autowired
    private VeiculoRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("Deve listar todos quando todos os filtros forem nulos")
    void deveListarTodosQuandoFiltrosForemNulos() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                null, null, null, null, null, null
        );

        assertThat(resultado).hasSize(2);
    }

    @Test
    @DisplayName("Deve filtrar por marca ignorando maiúsculas e minúsculas")
    void deveFiltrarPorMarcaIgnorandoCase() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                "hon", null, null, null, null, null
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getMarca()).isEqualTo("Honda");
    }

    @Test
    @DisplayName("Deve filtrar por modelo")
    void deveFiltrarPorModelo() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                null, "civi", null, null, null, null
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getModelo()).isEqualTo("Civic");
    }

    @Test
    @DisplayName("Deve filtrar por ano")
    void deveFiltrarPorAno() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                null, null, 2021, null, null, null
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getAno()).isEqualTo(2021);
    }

    @Test
    @DisplayName("Deve filtrar por valor mínimo")
    void deveFiltrarPorValorMinimo() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                null, null, null, 100000.0, null, null
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getModelo()).isEqualTo("Corolla");
    }

    @Test
    @DisplayName("Deve filtrar por valor máximo")
    void deveFiltrarPorValorMaximo() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                null, null, null, null, 100000.0, null
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getModelo()).isEqualTo("Civic");
    }

    @Test
    @DisplayName("Deve filtrar por placa ignorando maiúsculas e minúsculas")
    void deveFiltrarPorPlacaIgnorandoCase() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                null, null, null, null, null, "abc"
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getPlaca()).isEqualTo("ABC1234");
    }

    @Test
    @DisplayName("Deve aplicar múltiplos filtros ao mesmo tempo")
    void deveAplicarMultiplosFiltros() {
        salvarVeiculo("Civic", "Honda", 2020, 95000.0, "ABC1234");
        salvarVeiculo("City", "Honda", 2022, 115000.0, "XYZ9999");
        salvarVeiculo("Corolla", "Toyota", 2021, 105000.0, "DEF5678");

        List<Veiculo> resultado = repository.buscarComFiltros(
                "hon", null, null, 100000.0, null, null
        );

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getModelo()).isEqualTo("City");
    }

    private void salvarVeiculo(String modelo, String marca, Integer ano, Double valor, String placa) {
        Veiculo veiculo = new Veiculo();
        veiculo.setModelo(modelo);
        veiculo.setMarca(marca);
        veiculo.setAno(ano);
        veiculo.setValor(valor);
        veiculo.setPlaca(placa);

        entityManager.persist(veiculo);
    }
}