package repository;

import dto.TierListResumoResponse;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import model.TierList;

import java.util.List;

@ApplicationScoped
public class TierListRepository implements PanacheRepository<TierList> {

    @Inject
    EntityManager entityManager;

    public List<TierList> listarPorUsuario(Long usuarioId) {
        return list("usuarioId", usuarioId);
    }

    public List<TierList> listarPorSeason(String season) {
        return list("season", season);
    }

    public TierList buscarPorUsuarioESeason(Long usuarioId, String season) {
        return find("usuarioId = ?1 and season = ?2", usuarioId, season).firstResult();
    }

    public List<TierListResumoResponse> listarCardsPorSeason(String season) {
        return entityManager
                .createQuery("""
                    SELECT new dto.TierListResumoResponse(
                        tl.id,
                        tl.usuarioId,
                        tl.nome,
                        tl.season,
                        u.nickname,
                        u.fotoPerfil,
                        COUNT(i.id)
                    )
                    FROM TierList tl
                    LEFT JOIN TierListItem i ON i.tierList.id = tl.id
                    LEFT JOIN Usuario u ON u.id = tl.usuarioId
                    WHERE tl.season = :season
                    GROUP BY tl.id, tl.usuarioId, tl.nome, tl.season, u.nickname, u.fotoPerfil
                    ORDER BY tl.dataCriacao DESC
                """, TierListResumoResponse.class)
                .setParameter("season", season)
                .getResultList();
    }
}