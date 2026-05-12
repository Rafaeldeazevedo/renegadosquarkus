package model;


import jakarta.persistence.*;

@Entity
@Table(name = "jogador_comunidade_mania")
public class JogadorComunidadeMania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jogador_id")
    public JogadorComunidade jogador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "personagem_id")
    public Personagem personagem;

    @Column(name = "criado_por_nickname")
    public String criadoPorNickname;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    public String descricao;
}
