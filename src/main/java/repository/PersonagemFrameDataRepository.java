package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.PersonagemFrameData;

import java.util.List;

@ApplicationScoped
public class PersonagemFrameDataRepository implements PanacheRepository<PersonagemFrameData> {

    public List<PersonagemFrameData> buscarPorPersonagem(Integer personagemId) {
        return list("personagemId = ?1 order by id asc", personagemId);
    }
}