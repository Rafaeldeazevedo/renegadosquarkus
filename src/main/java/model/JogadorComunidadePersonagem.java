package model;

import jakarta.persistence.*;

@Entity
@Table(name = "jogador_comunidade_personagem")
public class JogadorComunidadePersonagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "jogador_id")
    private JogadorComunidade jogador;

    @ManyToOne(optional = false)
    @JoinColumn(name = "personagem_id")
    private Personagem personagem;

    public Long getId() {
        return id;
    }

    public JogadorComunidade getJogador() {
        return jogador;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setJogador(JogadorComunidade jogador) {
        this.jogador = jogador;
    }

    public void setPersonagem(Personagem personagem) {
        this.personagem = personagem;
    }
}