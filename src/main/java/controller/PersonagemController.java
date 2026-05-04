package controller;

import dto.PersonagemResponse;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import model.Personagem;

import java.util.List;

@Path("personagens")
@Produces(MediaType.APPLICATION_JSON)
public class PersonagemController {

    @GET
    public List<PersonagemResponse> listar() {
        List<Personagem> personagens = Personagem.listAll();

        return personagens.stream().map(personagem ->
                new PersonagemResponse(personagem.id, personagem.nome, personagem.imagem, false)).toList();
    }
}