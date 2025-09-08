package br.com.fiap.performancekids.service;

import br.com.fiap.performancekids.entity.Categoria;
import br.com.fiap.performancekids.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria salvar(Categoria c) {
        try {
            return repository.save(c);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Categoria já existe.");
        }
    }

    public List<Categoria> listarTodos() { return repository.findAll(); }

    public Categoria buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria ID " + id + " não encontrada."));
    }

    public Categoria atualizar(Long id, Categoria c) {
        Categoria existente = buscarPorId(id);
        existente.setNome(c.getNome());
        existente.setDescricao(c.getDescricao());
        return salvar(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Categoria ID " + id + " não encontrada para exclusão.");
        }
        repository.deleteById(id);
    }
}
