package com.frontEnd.gestao.domain.empresa.dto;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CadastroEmpresaDTO {


    private String name;
    private String email;
    private String  description;
    private String password;
    private  String webSite;

}
