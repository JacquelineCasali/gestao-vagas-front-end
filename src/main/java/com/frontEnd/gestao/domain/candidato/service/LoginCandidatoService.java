package com.frontEnd.gestao.domain.candidato.service;

import com.frontEnd.gestao.infra.token.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginCandidatoService {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
public Token login(String username,String password){
    RestTemplate restTemplate=new RestTemplate();

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    Map<String, String> data= new HashMap<>();
    data.put("username", username);
    data.put("password", password);

    HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

    var url =hostAPIGestaoVagas.concat("/candidato/login");

    // conexão banco de dados
    var result= restTemplate.postForObject(url,request, Token.class);

    //System.out.println(result);
    return result;

}

}
