package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.JogadorComunidadePlayerStyle;

@ApplicationScoped
public class JogadorComunidadePlayerStyleRepository implements PanacheRepository<JogadorComunidadePlayerStyle> {

    public JogadorComunidadePlayerStyle buscarPorJogadorEPersonagem(Long jogadorId, Long personagemId) {
        return find("jogador.id = ?1 and personagem.id = ?2", jogadorId, personagemId).firstResult();
    }
}
