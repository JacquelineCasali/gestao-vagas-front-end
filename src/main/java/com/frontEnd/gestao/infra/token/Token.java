package com.frontEnd.gestao.infra.token;

import lombok.Data;

import java.util.List;

@Data
public class Token {
    private String access_token;
    private List<String> roles;
private Long expirise_in;
}
