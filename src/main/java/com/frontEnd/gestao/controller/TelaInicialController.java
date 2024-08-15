package com.frontEnd.gestao.controller;

import com.frontEnd.gestao.domain.candidato.dto.CadastroCandidatoDTO;
import com.frontEnd.gestao.domain.candidato.service.CadastroCandidatoService;
import com.frontEnd.gestao.domain.candidato.service.IncricaoCandidatoVagaService;
import com.frontEnd.gestao.domain.candidato.service.LoginCandidatoService;
import com.frontEnd.gestao.domain.candidato.service.PerfilCandidatoService;
import com.frontEnd.gestao.domain.vagas.service.ListaVagasService;
import com.frontEnd.gestao.domain.vagas.service.VagasServices;
import com.frontEnd.gestao.infra.exceptions.FormatErrorMessage;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class TelaInicialController {



    @GetMapping
    public String telaInicial() {

        return "telaInicial";
    }



}
