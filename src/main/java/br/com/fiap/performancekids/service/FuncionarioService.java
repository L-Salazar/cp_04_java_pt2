package br.com.fiap.performancekids.service;

import br.com.fiap.performancekids.entity.Funcionario;
import br.com.fiap.performancekids.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    public Funcionario salvar(Funcionario f) {
        validarNegocio(f);
        try {
            return repository.save(f);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
    }

    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    public Funcionario buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Funcionário com ID " + id + " não encontrado."));
    }

    public Funcionario atualizar(Long id, Funcionario atual) {
        Funcionario existente = buscarPorId(id);

        // se o e-mail mudou, checa duplicidade
        if (!existente.getEmail().equalsIgnoreCase(atual.getEmail())
                && repository.existsByEmail(atual.getEmail())) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }

        existente.setNome(atual.getNome());
        existente.setEmail(atual.getEmail());
        existente.setSenha(atual.getSenha());
        existente.setCargo(atual.getCargo());

        validarNegocio(existente);
        return repository.save(existente);
    }

    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Funcionário com ID " + id + " não encontrado para exclusão.");
        }
        repository.deleteById(id);
    }

    private void validarNegocio(Funcionario f) {
        // Exemplo de regra: ADMIN deve ter senha com 8+ chars
        if ("ADMIN".equalsIgnoreCase(f.getCargo()) && (f.getSenha() == null || f.getSenha().length() < 8)) {
            throw new IllegalArgumentException("Para cargo ADMIN a senha deve ter pelo menos 8 caracteres.");
        }
    }
}
