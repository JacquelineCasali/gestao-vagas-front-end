package com.frontEnd.gestao.domain.vagas.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import com.frontEnd.gestao.domain.vagas.dto.VagaDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class VagasServices {
    @Value("${host.api.gestao}")
    private String hostAPIGestaoVagas;

    public List<VagaDTO> execute(String token, String filter) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        // adicionando a busca (filtro)
        var url =hostAPIGestaoVagas.concat("/candidato/vagas");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("filter", filter);


        // recebendo uma lista

        ParameterizedTypeReference<List<VagaDTO>> responseType = new ParameterizedTypeReference<List<VagaDTO>>() {};

        try {
            var result = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, request,responseType);
                   return result.getBody();
        } catch (HttpClientErrorException.Unauthorized ex) {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }

    }
}






