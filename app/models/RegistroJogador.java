package models;

import dtos.RegistroJogadorDTO;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.text.ParseException;

@Entity
public class RegistroJogador extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Jogador jogador;

    @NotNull
    private int qtdEliminacoes;

    @NotNull
    private int qtdBaixas;

    @NotNull
    private int qtdDano;

    @NotNull
    private int porcetagemHS;

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public int getQtdEliminacoes() {
        return qtdEliminacoes;
    }

    public void setQtdEliminacoes(int qtdEliminacoes) {
        this.qtdEliminacoes = qtdEliminacoes;
    }

    public int getQtdBaixas() {
        return qtdBaixas;
    }

    public void setQtdBaixas(int qtdBaixas) {
        this.qtdBaixas = qtdBaixas;
    }

    public int getQtdDano() {
        return qtdDano;
    }

    public void setQtdDano(int qtdDano) {
        this.qtdDano = qtdDano;
    }

    public int getPorcetagemHS() {
        return porcetagemHS;
    }

    public void setPorcetagemHS(int porcetagemHS) {
        this.porcetagemHS = porcetagemHS;
    }

    public static RegistroJogador converterDTORegistroJogador(RegistroJogadorDTO registrojogadorDTO) throws ParseException {

        RegistroJogador registroJogador = new RegistroJogador();

        //TODO implementar

        return registroJogador;

    }

}
