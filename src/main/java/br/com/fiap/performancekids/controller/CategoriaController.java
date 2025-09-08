package br.com.fiap.performancekids.controller;

import br.com.fiap.performancekids.entity.Categoria;
import br.com.fiap.performancekids.service.CategoriaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaController {

    private final CategoriaService service;

    public CategoriaController(CategoriaService service) {
        this.service = service;
    }

    // LISTAGEM
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", service.listarTodos());
        return "categorias/listagem"; // templates/categorias/listagem.html
    }

    // FORM: NOVO
    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/formulario"; // templates/categorias/formulario.html
    }

    // SALVAR (CREATE)
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("categoria") Categoria categoria,
                         BindingResult br,
                         RedirectAttributes ra) {
        if (br.hasErrors()) return "categorias/formulario";
        service.salvar(categoria);
        ra.addFlashAttribute("msg", "Categoria criada com sucesso!");
        return "redirect:/categorias";
    }

    // FORM: EDITAR
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("categoria", service.buscarPorId(id));
        return "categorias/formulario";
    }

    // ALTERAR (UPDATE via POST)
    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @Valid @ModelAttribute("categoria") Categoria categoria,
                          BindingResult br,
                          RedirectAttributes ra) {
        if (br.hasErrors()) return "categorias/formulario";
        service.atualizar(id, categoria);
        ra.addFlashAttribute("msg", "Categoria atualizada com sucesso!");
        return "redirect:/categorias";
    }

    // EXCLUIR (via link GET)
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.deletar(id);
        ra.addFlashAttribute("msg", "Categoria excluída com sucesso!");
        return "redirect:/categorias";
    }
}
