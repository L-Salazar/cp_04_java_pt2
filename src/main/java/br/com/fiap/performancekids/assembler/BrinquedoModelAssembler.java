package br.com.fiap.performancekids.assembler;

import br.com.fiap.performancekids.controller.BrinquedoController;
import br.com.fiap.performancekids.entity.Brinquedo;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class BrinquedoModelAssembler implements RepresentationModelAssembler<Brinquedo, EntityModel<Brinquedo>> {

    @Override
    public EntityModel<Brinquedo> toModel(Brinquedo brinquedo) {
        return EntityModel.of(brinquedo,
                // link para listar todos
                linkTo(methodOn(BrinquedoController.class).listarBrinquedos(null)).withRel("lista"),
                // link para editar (formulário)
                linkTo(methodOn(BrinquedoController.class).editarBrinquedo(brinquedo.getId(), null)).withRel("editar"),
                // link para excluir
                linkTo(methodOn(BrinquedoController.class).excluir(brinquedo.getId())).withRel("deletar")
        );
    }
}
