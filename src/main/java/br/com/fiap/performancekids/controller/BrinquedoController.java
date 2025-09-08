package br.com.fiap.performancekids.controller;

import br.com.fiap.performancekids.entity.Brinquedo;
import br.com.fiap.performancekids.entity.Categoria;
import br.com.fiap.performancekids.service.BrinquedoService;
import br.com.fiap.performancekids.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/brinquedos")
public class BrinquedoController {

    private final BrinquedoService brinquedoService;
    private final CategoriaService categoriaService;

    public BrinquedoController(BrinquedoService brinquedoService, CategoriaService categoriaService) {
        this.brinquedoService = brinquedoService;
        this.categoriaService = categoriaService;
    }

    // Listagem
    @GetMapping
    public String listarBrinquedos(Model model) {
        List<Brinquedo> brinquedos = brinquedoService.listarTodos();
        model.addAttribute("brinquedos", brinquedos);
        return "listagem";
    }

    // Formulário novo
    @GetMapping("/novo")
    public String novoBrinquedo(Model model) {
        model.addAttribute("brinquedo", new Brinquedo());
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "formulario";
    }

    // Salvar
    @PostMapping("/salvar")
    public String salvarBrinquedo(@ModelAttribute @Valid Brinquedo brinquedo,
                                  org.springframework.validation.BindingResult binding,
                                  Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodos());
            return "formulario";
        }
        brinquedoService.salvar(brinquedo);
        return "redirect:/brinquedos";
    }

    // Editar
    @GetMapping("/editar/{id}")
    public String editarBrinquedo(@PathVariable Long id, Model model) {
        Brinquedo brinquedo = brinquedoService.buscarPorId(id);
        model.addAttribute("brinquedo", brinquedo);
        model.addAttribute("categorias", categoriaService.listarTodos());
        return "formulario";
    }

    // Alterar
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @ModelAttribute @Valid Brinquedo brinquedo,
                          org.springframework.validation.BindingResult binding,
                          Model model) {
        if (binding.hasErrors()) {
            model.addAttribute("categorias", categoriaService.listarTodos());
            return "formulario";
        }
        brinquedoService.atualizar(id, brinquedo);
        return "redirect:/brinquedos";
    }


    // Excluir
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        brinquedoService.deletar(id);
        return "redirect:/brinquedos";
    }
}
