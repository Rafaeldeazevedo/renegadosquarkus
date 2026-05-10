package controller;


import dto.TierListItemRequest;
import dto.TierListRequest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import model.TierList;
import model.TierListItem;
import repository.TierListRepository;


@Path("/tier-lists")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TierListResource {

    @Inject
    TierListRepository tierListRepository;


    @POST
    @Transactional
    public Response criar(TierListRequest request) {
        if (request == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Request inválido.")
                    .build();
        }

        if (request.usuarioId == null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("usuarioId é obrigatório.")
                    .build();
        }

        if (request.nome == null || request.nome.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("nome é obrigatório.")
                    .build();
        }

        if (request.season == null || request.season.isBlank()) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("season é obrigatória.")
                    .build();
        }

        TierList existente = tierListRepository.buscarPorUsuarioESeason(
                request.usuarioId,
                request.season
        );

        if (existente != null) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("Você já criou uma Tier List para esta season.")
                    .build();
        }

        TierList tierList = new TierList();
        tierList.usuarioId = request.usuarioId;
        tierList.nome = request.nome;
        tierList.season = request.season;

        if (request.itens != null) {
            for (TierListItemRequest itemRequest : request.itens) {
                TierListItem item = new TierListItem();
                item.personagemId = itemRequest.personagemId;
                item.tier = itemRequest.tier;
                item.posicao = itemRequest.posicao;

                tierList.adicionarItem(item);
            }
        }

        tierListRepository.persist(tierList);

        return Response.status(Response.Status.CREATED)
                .entity(tierList)
                .build();
    }
    
    @GET
    public Response listarTodas() {
        return Response.ok(tierListRepository.listAll()).build();
    }

    @GET
    @Path("/usuario/{usuarioId}")
    public Response listarPorUsuario(@PathParam("usuarioId") Long usuarioId) {
        return Response.ok(tierListRepository.listarPorUsuario(usuarioId)).build();
    }

    @GET
    @Path("/season/{season}")
    public Response listarPorSeason(@PathParam("season") String season) {
        return Response.ok(tierListRepository.listarCardsPorSeason(season)).build();
    }

    @GET
    @Path("/{id}")
    public Response buscarPorId(@PathParam("id") Long id) {
        TierList tierList = tierListRepository.findById(id);

        if (tierList == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(tierList).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response excluir(@PathParam("id") Long id) {
        TierList tierList = tierListRepository.findById(id);

        if (tierList == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Tier List não encontrada.")
                    .build();
        }

        tierListRepository.delete(tierList);

        return Response.noContent().build();
    }
}
