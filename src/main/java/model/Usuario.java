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

        public String nickname;

        @Column(nullable = false)
        public Integer nivel;

        @Column(nullable = false)
        public Integer xp;
    }
