package com.frontEnd.gestao.domain.candidato.service;

import com.frontEnd.gestao.domain.candidato.dto.PerfilCandidatoDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.HttpClientErrorException.Unauthorized;
import java.util.Map;

import org.springframework.web.client.HttpClientErrorException;
@Service
public class PerfilCandidatoService {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
public PerfilCandidatoDTO execute(String token){
    RestTemplate restTemplate= new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(token);

    HttpEntity<Map<String, String>> request= new HttpEntity<>(headers);

try {

    var url =hostAPIGestaoVagas.concat("/candidato");
    var result= restTemplate.exchange(url, HttpMethod.GET,request, PerfilCandidatoDTO.class);


    return  result.getBody();
}catch (Unauthorized ex){
throw new   HttpClientErrorException(HttpStatus.UNAUTHORIZED);
}




}


}
