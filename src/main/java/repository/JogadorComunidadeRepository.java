package repository;


import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.JogadorComunidade;

@ApplicationScoped
public class JogadorComunidadeRepository implements PanacheRepository<JogadorComunidade> {
}