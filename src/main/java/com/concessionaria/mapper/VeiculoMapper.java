package com.concessionaria.mapper;

import com.concessionaria.DTO.VeiculoRequest;
import com.concessionaria.DTO.VeiculoResponse;
import com.concessionaria.entity.Veiculo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VeiculoMapper {

    Veiculo toEntity(VeiculoRequest request);

    VeiculoResponse toResponse(Veiculo veiculo);

    List<VeiculoResponse> toResponseList(List<Veiculo> veiculos);

}
