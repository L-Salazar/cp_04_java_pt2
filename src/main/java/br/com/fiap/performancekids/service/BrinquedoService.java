package br.com.fiap.performancekids.service;

import br.com.fiap.performancekids.entity.Brinquedo;
import br.com.fiap.performancekids.repository.BrinquedoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrinquedoService {

    @Autowired
    private BrinquedoRepository repository;

    public Brinquedo salvar(Brinquedo b) {
        return repository.save(b);
    }

    public List<Brinquedo> listarTodos() {
        return repository.findAll();
    }

    public Brinquedo buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Brinquedo com ID " + id + " não encontrado."));
    }

    // Atualização completa (PUT)
    public Brinquedo atualizar(Long id, Brinquedo atualizado) {
        Brinquedo existente = buscarPorId(id);
        // Atualiza os campos da entidade
        existente.setNome(atualizado.getNome());
        existente.setCategoria(atualizado.getCategoria());
        existente.setClassificacao(atualizado.getClassificacao());
        existente.setTamanho(atualizado.getTamanho());
        existente.setPreco(atualizado.getPreco());
        return repository.save(existente);
    }

    // Atualização parcial (PATCH)
    public Brinquedo atualizarParcial(Long id, Brinquedo brinquedoAtualizado) {
        Brinquedo existente = buscarPorId(id);

        if (brinquedoAtualizado.getNome() != null) {
            existente.setNome(brinquedoAtualizado.getNome());
        }
        if (brinquedoAtualizado.getCategoria() != null) {
            existente.setCategoria(brinquedoAtualizado.getCategoria());
        }
        if (brinquedoAtualizado.getClassificacao() != null) {
            existente.setClassificacao(brinquedoAtualizado.getClassificacao());
        }
        if (brinquedoAtualizado.getTamanho() != null) {
            existente.setTamanho(brinquedoAtualizado.getTamanho());
        }
        if (brinquedoAtualizado.getPreco() != null) {
            existente.setPreco(brinquedoAtualizado.getPreco());
        }

        return repository.save(existente);
    }

    // Deletar brinquedo
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Brinquedo com ID " + id + " não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }
}
