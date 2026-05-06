package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.JogadorComunidadePersonagem;

import java.util.List;

@ApplicationScoped
public class JogadorComunidadePersonagemRepository implements PanacheRepository<JogadorComunidadePersonagem> {

    public List<JogadorComunidadePersonagem> buscarPorJogadorId(Long jogadorId) {
        return list("jogador.id", jogadorId);
    }
}