package br.com.fiap.performancekids.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tb_performance_kids_funcionarios",
        uniqueConstraints = @UniqueConstraint(name = "uk_func_email", columnNames = "email_funcionario"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcionario")
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "nm_funcionario", nullable = false, length = 100)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(name = "email_funcionario", nullable = false, length = 150)
    private String email;

    @NotBlank
    @Size(min = 6, max = 60) // se depois usar hash, pode aumentar para 60
    @Column(name = "senha_funcionario", nullable = false, length = 60)
    private String senha;

    @NotBlank
    @Size(max = 50)
    @Column(name = "cargo_funcionario", nullable = false, length = 50)
    private String cargo; // ADMIN | OPERADOR | VENDEDOR...
}
