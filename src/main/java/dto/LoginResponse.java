package dto;


public class LoginResponse {

    public Long id;
    public String nome;
    public String email;
    public String nickname;
    public Integer nivel;
    public Integer xp;
    public  String token;
    public String fotoPerfil;
    public Boolean deveTrocarSenha;

    public LoginResponse(
            Long id,
            String nome,
            String email,
            String nickname,
            Integer nivel,
            Integer xp,
            String token,
            String fotoPerfil,
            Boolean deveTrocarSenha

    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nickname = nickname;
        this.nivel = nivel;
        this.xp = xp;
        this.token = token;
        this.fotoPerfil = fotoPerfil;
        this.deveTrocarSenha = deveTrocarSenha;

    }
}