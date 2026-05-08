package dto;

import java.util.List;

public class TierListRequest {

    public Long usuarioId;
    public String nome;
    public String season;
    public List<TierListItemRequest> itens;
}