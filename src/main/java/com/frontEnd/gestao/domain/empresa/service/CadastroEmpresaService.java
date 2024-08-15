package com.frontEnd.gestao.domain.empresa.service;

import com.frontEnd.gestao.domain.empresa.dto.CadastroEmpresaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CadastroEmpresaService {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
public String execute(CadastroEmpresaDTO cadastroEmpresaDTO){

    RestTemplate restTemplate= new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<CadastroEmpresaDTO> request = new HttpEntity<>(cadastroEmpresaDTO, headers);

    var url =hostAPIGestaoVagas.concat("/empresa");

    // conexão banco de dados
    var result= restTemplate.postForObject(url,request, String.class);
     System.out.println(result);
    return result;

   }



}



