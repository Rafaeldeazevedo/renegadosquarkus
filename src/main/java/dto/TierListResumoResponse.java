package dto;


public class TierListResumoResponse {

    public Long id;
    public Long usuarioId;
    public String nome;
    public String season;
    public String nickname;
    public String fotoPerfil;
    public Long totalItens;

    public TierListResumoResponse(
            Long id,
            Long usuarioId,
            String nome,
            String season,
            String nickname,
            String fotoPerfil,
            Long totalItens
    ) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.nome = nome;
        this.season = season;
        this.nickname = nickname;
        this.fotoPerfil = fotoPerfil;
        this.totalItens = totalItens;
    }
}