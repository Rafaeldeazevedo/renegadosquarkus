package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.Personagem;

@ApplicationScoped
public class PersonagemRepository implements PanacheRepository<Personagem> {
}