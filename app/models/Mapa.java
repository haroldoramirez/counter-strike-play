package models;

import dtos.MapaDTO;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import javax.validation.constraints.Size;
import java.text.ParseException;

@Entity
public class Mapa extends BaseModel {

    //MAP

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres.")
    @Column(length = 40, unique = true)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Mapa converterDTOMapa(MapaDTO mapaDTO) throws ParseException {

        Mapa mapa = new Mapa();
        mapa.setNome(mapaDTO.getNome());

        return mapa;

    }
}
