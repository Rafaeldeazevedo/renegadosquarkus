package controller;

import dto.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import repository.JogadorComunidadeManiaRepository;
import service.JogadorComunidadeService;

import java.util.List;

@Path("/jogadores-comunidade")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class JogadorComunidadeResource {



    @Inject
    JogadorComunidadeService jogadorComunidadeService;

    @Inject
    JogadorComunidadeManiaRepository maniaRepository;

    @GET
    public List<JogadorComunidadeResponse> listar() {
        return jogadorComunidadeService.listar();
    }

    @GET
    @Path("/{jogadorId}/personagens/{personagemId}/player-style")
    public PlayerStyleResponse buscarPlayerStyle(
            @PathParam("jogadorId") Long jogadorId,
            @PathParam("personagemId") Long personagemId
    ) {
        return jogadorComunidadeService.buscarPlayerStyle(jogadorId, personagemId);
    }


    @GET
    @Path("/{id}")
    public JogadorComunidadeResponse buscarPorId(@PathParam("id") Long id) {
        return jogadorComunidadeService.buscarPorId(id);
    }


    @POST
    public JogadorComunidadeResponse cadastrar(JogadorComunidadeRequest request) {
        return jogadorComunidadeService.cadastrar(request);
    }

    @GET
    @Path("/{id}/personagens")
    public List<PersonagemComunidadeResponse> listarPersonagensDoJogador(@PathParam("id") Long id) {
        return jogadorComunidadeService.listarPersonagensDoJogador(id);
    }

    @PUT
    @Path("/{jogadorId}/personagens/{personagemId}/player-style")
    public PlayerStyleResponse salvarPlayerStyle(
            @PathParam("jogadorId") Long jogadorId,
            @PathParam("personagemId") Long personagemId,
            PlayerStyleRequest request
    ) {
        return jogadorComunidadeService.salvarPlayerStyle(jogadorId, personagemId, request);
    }
    @GET
    @Path("/{jogadorId}/personagens/{personagemId}/manias")
    public List<ManiaResponse> listarManias(
            @PathParam("jogadorId") Long jogadorId,
            @PathParam("personagemId") Long personagemId
    ) {
        return jogadorComunidadeService.listarManias(jogadorId, personagemId);
    }

    @POST
    @Path("/{jogadorId}/personagens/{personagemId}/manias")
    public ManiaResponse criarMania(
            @PathParam("jogadorId") Long jogadorId,
            @PathParam("personagemId") Long personagemId,
            ManiaRequest request
    ) {
        return jogadorComunidadeService.criarMania(jogadorId, personagemId, request);
    }

    @PUT
    @Path("/manias/{maniaId}")
    public ManiaResponse alterarMania(
            @PathParam("maniaId") Long maniaId,
            ManiaRequest request
    ) {
        return jogadorComunidadeService.alterarMania(maniaId, request);
    }

    @DELETE
    @Path("/manias/{maniaId}")
    public void excluirMania(@PathParam("maniaId") Long maniaId) {
        jogadorComunidadeService.excluirMania(maniaId);
    }

}