package models;

import dtos.MapaDTO;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import jakarta.persistence.*;

import javax.validation.constraints.Size;
import java.text.ParseException;
import java.util.Calendar;

@Entity
public class Mapa extends Model {

    //MAP

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @NotNull
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres.")
    @Column(length = 40, unique = true)
    private String nome;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar dataAlteracao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Calendar getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public static Mapa converterDTOMapa(MapaDTO mapaDTO) throws ParseException {

        Mapa mapa = new Mapa();
        mapa.setNome(mapaDTO.getNome());

        return mapa;

    }

}