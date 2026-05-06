package model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "jogador_comunidade")
public class JogadorComunidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "nome", nullable = false, length = 100)
    public String nome;

    @Column(name = "tekken_id", nullable = false, length = 100)
    public String tekkenId;

    @Column(name = "foto", length = 255)
    public String foto;

    @OneToMany(mappedBy = "jogador", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<JogadorComunidadePersonagem> personagens = new ArrayList<>();
}
