package dto;


public class LoginResponse {
    public Long id;
    public String nome;
    public String email;
    public String nickname;
    public Integer nivel;
    public Integer xp;

    public LoginResponse(
            Long id,
            String nome,
            String email,
            String nickname,
            Integer nivel,
            Integer xp
    ) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.nickname = nickname;
        this.nivel = nivel;
        this.xp = xp;
    }

}
