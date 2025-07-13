package dtos;

import models.Mapa;
import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MapaDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "O nome é obrigatório.")
    @Constraints.MaxLength(value = 40, message = "O nome deve ter no máximo 40 caracteres.")
    private String nome;

    private Calendar dataCadastro;
    private Calendar dataAlteracao;

    /** Necessario para instanciar o form */
    public MapaDTO() {}

    public MapaDTO(String nome, Calendar dataAlteracao, Calendar dataCadastro) {
        this.nome = nome;
        this.dataAlteracao = dataAlteracao;
        this.dataCadastro = dataCadastro;
    }

    public MapaDTO(String nome) {
        this.nome = nome;
    }

    public static MapaDTO converterMapaDTO(Mapa mapa) {

        MapaDTO MapaDTO = new MapaDTO();

        MapaDTO.setNome(mapa.getNome());
        MapaDTO.setDataCadastro(mapa.getDataCadastro());
        MapaDTO.setDataAlteracao(mapa.getDataAlteracao());

        return MapaDTO;

    }

    public String getNome() {
        return nome.trim().toUpperCase();
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    @Override
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        if (nome != null && nome.trim().equalsIgnoreCase("admin")) {
            errors.add(new ValidationError(Scala.toSeq(List.of("nome")), Scala.toSeq(List.of("O nome 'admin' não é permitido."))));
        }

        return errors.isEmpty() ? null : errors;
    }
    
}