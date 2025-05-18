package models;

import dtos.JogadorDTO;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import javax.validation.constraints.Size;
import java.text.ParseException;
import java.util.Calendar;

@Entity
public class Jogador extends BaseModel {

    //PLAYER

    private static final long serialVersionUID = 1L;

    @NotNull
    @Size(max = 40, message = "O nome deve ter no máximo 40 caracteres.")
    @Column(length = 40)
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public static Jogador converterDTOJogador(JogadorDTO jogadorDTO) throws ParseException {

        Calendar dataHoraCadastro = Calendar.getInstance();

        Jogador jogador = new Jogador();

        jogador.setNome(jogadorDTO.getNome());
        jogador.setDataCadastro(dataHoraCadastro);
        jogador.setDataAlteracao(dataHoraCadastro);

        return jogador;

    }

}