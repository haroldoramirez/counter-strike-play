package models;

import dtos.RegistroPartidaJogadorDTO;
import io.ebean.annotation.NotNull;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import models.enums.StatusPartida;

@Entity
public class RegistroPartidaJogador extends BaseModel {

    private static final long serialVersionUID = 1L;

    @ManyToOne
    private Jogador jogador;

    @ManyToOne
    private Mapa mapa;

    @Enumerated(value = EnumType.STRING)
    private StatusPartida statusPartida;

    @NotNull
    private int qtdEliminacoes;

    @NotNull
    private int qtdBaixas;

    @NotNull
    private int qtdDano;

    @NotNull
    private int porcetagemHS;

    @NotNull
    private Boolean vitoria;

    @NotNull
    private int qtdDanoUtilitario;

    @NotNull
    private int qtdInimigosCegos;

    public Mapa getMapa() {
        return mapa;
    }

    public void setMapa(Mapa mapa) {
        this.mapa = mapa;
    }

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

    public Boolean getVitoria() {
        return vitoria;
    }

    public void setVitoria(Boolean vitoria) {
        this.vitoria = vitoria;
    }

    public int getQtdDanoUtilitario() {
        return qtdDanoUtilitario;
    }

    public void setQtdDanoUtilitario(int qtdDanoUtilitario) {
        this.qtdDanoUtilitario = qtdDanoUtilitario;
    }

    public int getQtdInimigosCegos() {
        return qtdInimigosCegos;
    }

    public void setQtdInimigosCegos(int qtdInimigosCegos) {
        this.qtdInimigosCegos = qtdInimigosCegos;
    }

    public StatusPartida getStatusPartida() {
        return statusPartida;
    }

    public void setStatusPartida(StatusPartida statusPartida) {
        this.statusPartida = statusPartida;
    }

    public static RegistroPartidaJogador converterRegistroJogadorDTORegistroJogador(RegistroPartidaJogadorDTO registroJogadorDTO) {

        RegistroPartidaJogador registroJogador = new RegistroPartidaJogador();

        //TODO tratar objetos que vem do DTO
        registroJogador.setJogador(new Jogador());
        registroJogador.setMapa(new Mapa());
        registroJogador.setStatusPartida(StatusPartida.DERROTA);

        registroJogador.setVitoria(registroJogadorDTO.getVitoria());
        registroJogador.setQtdDanoUtilitario(registroJogadorDTO.getQtdDanoUtilitario());
        registroJogador.setQtdInimigosCegos(registroJogadorDTO.getQtdInimigosCegos());
        registroJogador.setPorcetagemHS(registroJogadorDTO.getPorcetagemHS());
        registroJogador.setQtdBaixas(registroJogadorDTO.getQtdBaixas());
        registroJogador.setQtdDano(registroJogadorDTO.getQtdDano());
        registroJogador.setQtdEliminacoes(registroJogadorDTO.getQtdEliminacoes());

        return registroJogador;

    }

}
