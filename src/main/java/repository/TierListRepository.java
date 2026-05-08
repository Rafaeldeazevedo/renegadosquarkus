package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import model.TierList;

import java.util.List;

@ApplicationScoped
public class TierListRepository implements PanacheRepository<TierList> {

    public List<TierList> listarPorUsuario(Long usuarioId) {
        return list("usuarioId", usuarioId);
    }

    public List<TierList> listarPorSeason(String season) {
        return list("season", season);
    }
}