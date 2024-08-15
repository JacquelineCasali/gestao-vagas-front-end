package com.frontEnd.gestao.domain.candidato.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroCandidatoDTO {
    private String username;
    private String name;
    private String email;
private String password;
    private String  description;



}
