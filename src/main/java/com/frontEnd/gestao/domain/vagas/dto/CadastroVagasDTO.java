package com.frontEnd.gestao.domain.vagas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroVagasDTO {


    private String  description;
    private String beneficio;
    private  String nivelDaVaga;
    private  String modalidadeVaga;
    private String requisitos;
}
