package br.com.fiap.performancekids.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "tb_performance_kids_categorias",
        uniqueConstraints = @UniqueConstraint(name = "uk_categoria_nome", columnNames = "nm_categoria"))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_categoria")
    private Long id;

    @NotBlank
    @Size(max = 30)
    @Column(name = "nm_categoria", nullable = false, length = 30)
    private String nome; // Ex.: BOLA, TENIS, MEIA, ROUPA, RAQUETE, ACESSORIO

    @Size(max = 150)
    @Column(name = "ds_categoria", length = 150)
    private String descricao;
}
