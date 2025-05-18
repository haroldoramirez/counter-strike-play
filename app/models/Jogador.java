package models;

import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;

@Entity
public class Jogador extends BaseModel {

    //PLAYER

    private static final long serialVersionUID = 1L;

    @NotNull
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}