package br.com.fiap.performancekids.controller;

import br.com.fiap.performancekids.entity.Funcionario;
import br.com.fiap.performancekids.service.FuncionarioService;
import br.com.fiap.performancekids.service.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService service;

    public FuncionarioController(FuncionarioService service) {
        this.service = service;
    }

    // LISTA
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("funcionarios", service.listarTodos());
        return "funcionarios/listagem";
    }

    // FORM NOVO
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionarios/formulario";
    }

    // SALVAR (CREATE)
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute @Valid Funcionario funcionario,
                         BindingResult binding,
                         Model model) {
        if (binding.hasErrors()) {
            return "funcionarios/formulario";
        }
        service.salvar(funcionario);
        return "redirect:/funcionarios";
    }

    // FORM EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Funcionario f = service.buscarPorId(id);
        // Por segurança, não pré-preenche a senha no formulário
        f.setSenha(null);
        model.addAttribute("funcionario", f);
        return "funcionarios/formulario";
    }

    // ALTERAR (UPDATE)
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @ModelAttribute @Valid Funcionario funcionario,
                          BindingResult binding,
                          Model model) {
        // garante que o ID do path prevaleça
        funcionario.setId(id);
        if (binding.hasErrors()) {
            return "funcionarios/formulario";
        }
        service.atualizar(id, funcionario);
        return "redirect:/funcionarios";
    }

    // EXCLUIR
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/funcionarios";
    }

    // (Opcional) Tratar "não encontrado" voltando para a lista
    @ExceptionHandler(ResourceNotFoundException.class)
    public String notFound(ResourceNotFoundException ex) {
        return "redirect:/funcionarios";
    }
}
