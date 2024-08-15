package com.frontEnd.gestao.domain.vagas.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListaVagaDTO {

    // O QUE PASSA POR USUARIO
// para exemplo do swager

//    REQUIRED  campo obrigatorio
private  Long id;
    private String  description;
    private String beneficio;
    private  String nivelDaVaga;
    private  String modalidadeVaga;
private  String empresaId;
private String empresa;
private Date createdAt;
    private String requisitos;

}
