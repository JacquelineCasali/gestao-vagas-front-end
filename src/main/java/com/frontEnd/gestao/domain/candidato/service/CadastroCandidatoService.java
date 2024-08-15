package com.frontEnd.gestao.domain.candidato.service;

import com.frontEnd.gestao.domain.candidato.dto.CadastroCandidatoDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
@Service
public class CadastroCandidatoService {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
public void execute(CadastroCandidatoDTO cadastroCandidatoDTO){

    RestTemplate restTemplate= new RestTemplate();
    HttpHeaders headers = new HttpHeaders();

    headers.setContentType(MediaType.APPLICATION_JSON);

    HttpEntity<CadastroCandidatoDTO> request = new HttpEntity<>(cadastroCandidatoDTO, headers);

    var url =hostAPIGestaoVagas.concat("/candidato");

    // conexão banco de dados
    var result= restTemplate.postForObject(url,request, String.class);
     System.out.println(result);
    //return result;

   }



}



