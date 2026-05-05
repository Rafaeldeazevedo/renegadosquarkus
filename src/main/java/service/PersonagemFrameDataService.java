package service;

import dto.PersonagemFrameDataDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.PersonagemFrameData;
import repository.PersonagemFrameDataRepository;

import java.util.List;

@ApplicationScoped
public class PersonagemFrameDataService {

    @Inject
    PersonagemFrameDataRepository repository;

    public List<PersonagemFrameDataDTO> buscarPorPersonagem(Integer personagemId) {
        List<PersonagemFrameData> lista = repository.buscarPorPersonagem(personagemId);

        return lista.stream()
                .map(item -> new PersonagemFrameDataDTO(
                        item.getId(),
                        item.getPersonagemId(),
                        item.getComando(),
                        item.getHitLevel(),
                        item.getDano(),
                        item.getStartup(),
                        item.getBlockFrame(),
                        item.getHitFrame(),
                        item.getCounterHitFrame(),
                        item.getObservacao()
                ))
                .toList();
    }
}