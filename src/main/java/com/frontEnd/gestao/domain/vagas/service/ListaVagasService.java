package com.frontEnd.gestao.domain.vagas.service;

import com.frontEnd.gestao.domain.vagas.dto.ListaVagaDTO;
import com.frontEnd.gestao.domain.vagas.dto.VagaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ListaVagasService {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;
    public List<ListaVagaDTO> execute(String token) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ParameterizedTypeReference<List<ListaVagaDTO>> responseType = new ParameterizedTypeReference<List<ListaVagaDTO>>() {
        };

        var url =hostAPIGestaoVagas.concat("/candidato/listavagas");

        var result = restTemplate.exchange(url, HttpMethod.GET, httpEntity, responseType);
        return result.getBody();
    }
}