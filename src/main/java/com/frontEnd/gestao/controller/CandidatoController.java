package com.frontEnd.gestao.controller;

import com.frontEnd.gestao.domain.candidato.dto.CadastroCandidatoDTO;
import com.frontEnd.gestao.domain.candidato.service.CadastroCandidatoService;
import com.frontEnd.gestao.domain.candidato.service.IncricaoCandidatoVagaService;
import com.frontEnd.gestao.domain.candidato.service.LoginCandidatoService;
import com.frontEnd.gestao.domain.candidato.service.PerfilCandidatoService;

import com.frontEnd.gestao.domain.vagas.service.ListaVagasService;
import com.frontEnd.gestao.infra.exceptions.FormatErrorMessage;
import com.frontEnd.gestao.domain.vagas.service.VagasServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/candidato")
public class CandidatoController {


    @Autowired
    private LoginCandidatoService candidatoService;

    @Autowired
    private PerfilCandidatoService perfilCandidatoService;

    @Autowired
    private VagasServices vagasServices;

    @Autowired
    private IncricaoCandidatoVagaService incricaoCandidatoVagaService;

    @Autowired
    private CadastroCandidatoService cadastroCandidatoService;


    @Autowired
    private  ListaVagasService listaVagasService;





//    login
    @GetMapping("/login")
    public String login() {

        return "candidato/login";
    }


    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String username, String password) {


        try {
            var token = this.candidatoService.login(username, password);
            var grants = token.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);

            auth.setDetails(token.getAccess_token());
            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token);

            return "redirect:/candidato/vagas/lista";

        } catch (HttpClientErrorException e) {
            // mensagem de erro
            redirectAttributes.addFlashAttribute("error_message", "Usuário/Senha inválido");
            return "redirect:/candidato/login";
        }
    }


//    perfil
    @GetMapping("/perfil")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String perfilCandidato(Model model) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            var user = this.perfilCandidatoService.execute(authentication.getDetails().toString());
            model.addAttribute("user", user);
            return "candidato/perfilCandidato";
        } catch (HttpClientErrorException e) {
            return "redirect:/candidato/login";
        }
    }


//    vagas
    @GetMapping("/vagas")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String vagas(Model model, String filter) {

        // System.out.println(filter);
        try {
            // trazendo a busca
            if (filter != null) {
                var vagas = this.vagasServices.execute(getToken(), filter);
                model.addAttribute("vagas", vagas);
                //System.out.println(vagas);
            }

        } catch (HttpClientErrorException e) {
            return "redirect:/candidato/login";
        }
        return "candidato/vagasCandidato";
    }

//candidatar as vagas
    @PostMapping("/vagas/inscricao")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String aplicarVaga(@RequestParam("vagaId") Long vagaId) {
        this.incricaoCandidatoVagaService.execute(getToken(), vagaId);
        return "redirect:/candidato/vagas";
    }


//    cadastro
    @GetMapping("/cadastro")
    public String cadastro(Model model) {
model.addAttribute("candidato",new CadastroCandidatoDTO());
        return "candidato/cadastro";
    }

    @PostMapping("/cadastro")
    public String save(CadastroCandidatoDTO candidato, Model model) {

  try{
      this.cadastroCandidatoService.execute(candidato);
  }catch (HttpClientErrorException ex){

      model.addAttribute("error_message",FormatErrorMessage.formatErrorMessage(ex.getResponseBodyAsString()));

  }
        model.addAttribute("candidato",candidato);
        return "candidato/cadastro";
    }



    //    perfil
    @GetMapping("/vagas/lista")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String listaVagas(Model model) {
        var result= this.listaVagasService.execute(getToken());
        model.addAttribute("vagas",result);


        System.out.println(result);
        return "vaga/listaVagasCandidato";


    }








    @GetMapping("/logout")
    @PreAuthorize("hasRole('CANDIDATO')")
    public String sair(HttpSession session) {
        SecurityContextHolder.getContext().setAuthentication(null);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
        session.setAttribute("token", null);
        return "redirect:/";

    }


    private String getToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getDetails().toString();
    }


}
