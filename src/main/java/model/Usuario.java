package model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario extends PanacheEntity {

        @Column(nullable = false)
        public String nome;

        @Column(nullable = false, unique = true)
        public String email;

        @Column(nullable = false)
        public String senha;

        @Column(name = "senha_temporaria")
        public Boolean senhaTemporaria = false;

        @Column(name = "perfil")
        public String perfil = "USER";

        public String nickname;

        @Column(nullable = false)
        public Integer nivel;

        @Column(nullable = false)
        public Integer xp;

        @Column(name = "foto_perfil", columnDefinition = "TEXT")
        public String fotoPerfil;
    }
