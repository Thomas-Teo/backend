package com.concessionaria.repository;

import com.concessionaria.entity.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
    @Query("""
    SELECT v FROM Veiculo v
    WHERE (:marca IS NULL OR LOWER(v.marca) LIKE LOWER(CONCAT('%', :marca, '%')))
    AND (:modelo IS NULL OR LOWER(v.modelo) LIKE LOWER(CONCAT('%', :modelo, '%')))
    AND (:ano IS NULL OR v.ano = :ano)
    AND (:valorMin IS NULL OR v.valor >= :valorMin)
    AND (:valorMax IS NULL OR v.valor <= :valorMax)
    AND (:placa IS NULL OR LOWER(v.placa) LIKE LOWER(CONCAT('%', :placa, '%')))""")
    List<Veiculo> buscarComFiltros(String marca, String modelo, Integer ano, Double valorMin, Double valorMax, String placa);
}
