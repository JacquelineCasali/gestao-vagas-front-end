package com.frontEnd.gestao.domain.candidato.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerfilCandidatoDTO {

    private String  description;
    private  Long id;
    private String name;
    private String email;
    private String username;
}
