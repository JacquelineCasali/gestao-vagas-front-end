package com.frontEnd.gestao.domain.vagas.service;

import com.frontEnd.gestao.domain.vagas.dto.CadastroVagasDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CadastroVagasService {

    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
    public String execute(CadastroVagasDTO cadastroVagasDTO, String token){

        RestTemplate restTemplate=new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CadastroVagasDTO> request = new HttpEntity<>(cadastroVagasDTO, headers);

        var url =hostAPIGestaoVagas.concat("/empresa/vagas");

        // conexão banco de dados
        var result= restTemplate.postForObject(url,request, String.class);

        // System.out.println(result);
        return result;

    }


}
