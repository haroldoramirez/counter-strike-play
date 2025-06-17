package models;

import dtos.RegistroPartidaJogadorDTO;
import io.ebean.Model;
import io.ebean.annotation.NotNull;
import jakarta.persistence.*;
import models.enums.StatusPartida;

import java.util.Calendar;

@Entity
public class RegistroPartidaJogador extends Model {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

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
    private int qtdDanoUtilitario;

    @NotNull
    private int qtdInimigosCegos;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar dataCadastro;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Calendar dataAlteracao;

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

    public static RegistroPartidaJogador converterRegistroJogadorDTORegistroJogador(RegistroPartidaJogadorDTO registroJogadorDTO) {

        RegistroPartidaJogador registroJogador = new RegistroPartidaJogador();

        registroJogador.setQtdDanoUtilitario(registroJogadorDTO.getQtdDanoUtilitario());
        registroJogador.setQtdInimigosCegos(registroJogadorDTO.getQtdInimigosCegos());
        registroJogador.setPorcetagemHS(registroJogadorDTO.getPorcetagemHS());
        registroJogador.setQtdBaixas(registroJogadorDTO.getQtdBaixas());
        registroJogador.setQtdDano(registroJogadorDTO.getQtdDano());
        registroJogador.setQtdEliminacoes(registroJogadorDTO.getQtdEliminacoes());
        StatusPartida statusPartida = StatusPartida.valueOf(registroJogadorDTO.getStatusPartida());
        registroJogador.setStatusPartida(statusPartida);

        return registroJogador;

    }

}