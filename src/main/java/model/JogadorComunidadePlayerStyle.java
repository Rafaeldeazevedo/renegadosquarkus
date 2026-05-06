package model;

import jakarta.persistence.*;

@Entity
@Table(name = "jogador_comunidade_player_style")
public class JogadorComunidadePlayerStyle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jogador_id")
    public JogadorComunidade jogador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "personagem_id")
    public Personagem personagem;

    @Column(name = "estilo_jogo", columnDefinition = "TEXT")
    public String estiloJogo;
}