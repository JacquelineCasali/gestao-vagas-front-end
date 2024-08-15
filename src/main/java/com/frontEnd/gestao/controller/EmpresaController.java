package com.frontEnd.gestao.controller;


import com.frontEnd.gestao.domain.empresa.dto.CadastroEmpresaDTO;
import com.frontEnd.gestao.domain.empresa.service.CadastroEmpresaService;
import com.frontEnd.gestao.domain.empresa.service.LoginEmpresaService;
import com.frontEnd.gestao.domain.vagas.service.ListaVagasEmpresasService;
import com.frontEnd.gestao.infra.exceptions.FormatErrorMessage;
import com.frontEnd.gestao.domain.vagas.dto.CadastroVagasDTO;
import com.frontEnd.gestao.domain.vagas.service.CadastroVagasService;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {

    @Autowired
    private CadastroEmpresaService cadastroEmpresaService;

    @Autowired
    private LoginEmpresaService loginEmpresaService;


    @Autowired
    private CadastroVagasService cadastroVagasService;



    @Autowired
    ListaVagasEmpresasService listaVagasEmpresasService;

    @GetMapping("/cadastro")
    public String cadastro(Model model) {
        model.addAttribute("empresa", new CadastroEmpresaDTO());
        return "empresa/cadastro";
    }

    @PostMapping("/cadastro")
    public String save(Model model, CadastroEmpresaDTO cadastroEmpresaDTO) {

        try {
            this.cadastroEmpresaService.execute(cadastroEmpresaDTO);
        
            model.addAttribute("empresa", new CadastroEmpresaDTO());

        } catch (HttpClientErrorException ex) {
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(ex.getResponseBodyAsString()));
            model.addAttribute("empresa", cadastroEmpresaDTO);
        }


        return "empresa/login";
    }

    //    login
    @GetMapping("/login")
    public String login() {

        return "empresa/login";
    }

    @PostMapping("/signIn")
    public String signIn(RedirectAttributes redirectAttributes, HttpSession session, String name, String password) {


        try {
            var token = this.loginEmpresaService.login(name, password);
            System.out.println(token);
            var grants = token.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(null, null, grants);

            auth.setDetails(token.getAccess_token());
            SecurityContextHolder.getContext().setAuthentication(auth);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
            session.setAttribute("token", token);

            return "redirect:/empresa/vagas/lista";

        } catch (HttpClientErrorException e) {
            // mensagem de erro
            redirectAttributes.addFlashAttribute("error_message", "Usuário/Senha inválido");
            return "redirect:/empresa/login";


        }
    }

    // vagas
    @GetMapping("/vagas")
    @PreAuthorize("hasRole('EMPRESA')")
    public String vagas(Model model) {

        model.addAttribute("vagas", new CadastroVagasDTO());
        return "vaga/cadastrovagas";
    }

    @PostMapping("/vagas")
    @PreAuthorize("hasRole('EMPRESA')")
    public String cadastroVagas(RedirectAttributes redirectAttributes, CadastroVagasDTO cadastroVagasDTO) {


        try {
            this.cadastroVagasService.execute(cadastroVagasDTO, getToken());
            return "redirect:/empresa/vagas/lista";

        } catch (HttpClientErrorException ex) {
            redirectAttributes.addFlashAttribute("error_message", "Dados incorretos revise os campos");

            return "redirect:/empresa/vagas";
        }



    }



    //    perfil
    @GetMapping("/vagas/lista")
    @PreAuthorize("hasRole('EMPRESA')")
    public String listaVagas(Model model) {
var result= this.listaVagasEmpresasService.execute(getToken());
       model.addAttribute("vagas",result);


        System.out.println(result);
        return "vaga/listaVagas";


    }


    @GetMapping("/logout")
    @PreAuthorize("hasRole('EMPRESA')")
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
