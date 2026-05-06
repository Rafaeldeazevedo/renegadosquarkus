package repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.JogadorComunidadeMania;

import java.util.List;

@ApplicationScoped
public class JogadorComunidadeManiaRepository implements PanacheRepository<JogadorComunidadeMania> {

    public List<JogadorComunidadeMania> buscarPorJogadorEPersonagem(Long jogadorId, Long personagemId) {
        return list("jogador.id = ?1 and personagem.id = ?2 order by id asc", jogadorId, personagemId);
    }
}