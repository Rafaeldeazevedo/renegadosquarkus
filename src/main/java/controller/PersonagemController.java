package controller;

import dto.PersonagemResponse;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import model.Personagem;
import model.Usuario;
import model.UsuarioFavorito;

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

    @GET
    @Path("/usuario/{usuarioId}")
    public List<PersonagemResponse> listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        Usuario usuario = Usuario.findById(usuarioId);

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado.", 404);
        }

        List<Personagem> personagens = Personagem.listAll();

        return personagens.stream()
                .map(personagem -> {
                    boolean favorito = UsuarioFavorito.count(
                            "usuario = ?1 and personagem = ?2",
                            usuario,
                            personagem
                    ) > 0;

                    return new PersonagemResponse(
                            personagem.id,
                            personagem.nome,
                            personagem.imagem,
                            favorito
                    );
                })
                .toList();
    }

    @POST
    @Path("/{personagemId}/favorito/usuario/{usuarioId}")
    @Transactional
    public void favoritar(
            @PathParam("personagemId") Long personagemId,
            @PathParam("usuarioId") Long usuarioId
    ) {
        Usuario usuario = Usuario.findById(usuarioId);

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado.", 404);
        }

        Personagem personagem = Personagem.findById(personagemId);

        if (personagem == null) {
            throw new WebApplicationException("Personagem não encontrado.", 404);
        }

        UsuarioFavorito existente = UsuarioFavorito.find(
                "usuario = ?1 and personagem = ?2",
                usuario,
                personagem
        ).firstResult();

        if (existente != null) {
            return;
        }

        UsuarioFavorito favorito = new UsuarioFavorito();
        favorito.usuario = usuario;
        favorito.personagem = personagem;
        favorito.persist();
    }

    @DELETE
    @Path("/{personagemId}/favorito/usuario/{usuarioId}")
    @Transactional
    public void removerFavorito(
            @PathParam("personagemId") Long personagemId,
            @PathParam("usuarioId") Long usuarioId
    ) {
        Usuario usuario = Usuario.findById(usuarioId);

        if (usuario == null) {
            throw new WebApplicationException("Usuário não encontrado.", 404);
        }

        Personagem personagem = Personagem.findById(personagemId);

        if (personagem == null) {
            throw new WebApplicationException("Personagem não encontrado.", 404);
        }

        UsuarioFavorito.delete(
                "usuario = ?1 and personagem = ?2",
                usuario,
                personagem
        );
    }
}