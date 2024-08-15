package com.frontEnd.gestao.domain.candidato.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class IncricaoCandidatoVagaService {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
    public String execute (String token, Long idVaga){
        RestTemplate restTemplate=new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
      headers.setBearerAuth(token);


//        Map<String, String> data= new HashMap<>();
//        data.put("idVaga", idVaga.toString());


        HttpEntity<Long> request = new HttpEntity<>(idVaga, headers);

        var url =hostAPIGestaoVagas.concat("/candidato/vagas/inscricao");
        // conexão banco de dados
        var result= restTemplate.postForObject(url,request, String.class);

       // System.out.println(result);
        return result;

    }



}
