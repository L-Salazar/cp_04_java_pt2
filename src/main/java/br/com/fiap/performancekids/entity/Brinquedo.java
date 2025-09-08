package br.com.fiap.performancekids.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import lombok.*;

@Entity
@Table(name = "tb_performance_kids_brinquedos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Brinquedo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_brinquedo")
    private Long id;

    @NotBlank
    @Size(min = 3, max = 100)
    @Column(name = "nm_brinquedo", nullable = false, length = 100)
    private String nome;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_categoria", nullable = false)
    private Categoria categoria; // substitui o campo String "tipo"

    @NotBlank
    @Size(max = 10)
    @Column(name = "cl_brinquedo", nullable = false, length = 10)
    private String classificacao; // 3-5 | 6-8 | 9-12

    @NotBlank
    @Size(max = 10)
    @Column(name = "tm_brinquedo", nullable = false, length = 10)
    private String tamanho;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 8, fraction = 2)
    @Column(name = "vl_preco", nullable = false, precision = 10, scale = 2)
    private BigDecimal preco;
}
