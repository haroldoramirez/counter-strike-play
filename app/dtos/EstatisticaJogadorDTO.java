package dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EstatisticaJogadorDTO {

    private String nomeJogador;
    private String mapaMaisJogado;
    private int quantidadeVitorias;
    private int quantidadeDerrotas;
    private int quantidadeEmpates;
    private int totalQtdEliminacoes;
    private int totalQtdBaixas;
    private int totalQtdDano;
    private int totalMaiorPorcetagemHS;
    private int totalQtdDanoUtilitario;
    private int totalQtdInimigosCegos;
    private BigDecimal totalKdr;
    private int posicaoJogador;

    public String getNomeJogador() {
        return nomeJogador;
    }

    public void setNomeJogador(String nomeJogador) {
        this.nomeJogador = nomeJogador;
    }

    public String getMapaMaisJogado() {
        return mapaMaisJogado;
    }

    public void setMapaMaisJogado(String mapaMaisJogado) {
        this.mapaMaisJogado = mapaMaisJogado;
    }

    public int getQuantidadeVitorias() {
        return quantidadeVitorias;
    }

    public void setQuantidadeVitorias(int quantidadeVitorias) {
        this.quantidadeVitorias = quantidadeVitorias;
    }

    public int getTotalQtdEliminacoes() {
        return totalQtdEliminacoes;
    }

    public void setTotalQtdEliminacoes(int totalQtdEliminacoes) {
        this.totalQtdEliminacoes = totalQtdEliminacoes;
    }

    public int getTotalQtdBaixas() {
        return totalQtdBaixas;
    }

    public void setTotalQtdBaixas(int totalQtdBaixas) {
        this.totalQtdBaixas = totalQtdBaixas;
    }

    public int getTotalQtdDano() {
        return totalQtdDano;
    }

    public void setTotalQtdDano(int totalQtdDano) {
        this.totalQtdDano = totalQtdDano;
    }

    public int getTotalMaiorPorcetagemHS() {
        return totalMaiorPorcetagemHS;
    }

    public void setTotalMaiorPorcetagemHS(int totalMaiorPorcetagemHS) {
        this.totalMaiorPorcetagemHS = totalMaiorPorcetagemHS;
    }

    public int getTotalQtdDanoUtilitario() {
        return totalQtdDanoUtilitario;
    }

    public void setTotalQtdDanoUtilitario(int totalQtdDanoUtilitario) {
        this.totalQtdDanoUtilitario = totalQtdDanoUtilitario;
    }

    public int getTotalQtdInimigosCegos() {
        return totalQtdInimigosCegos;
    }

    public void setTotalQtdInimigosCegos(int totalQtdInimigosCegos) {
        this.totalQtdInimigosCegos = totalQtdInimigosCegos;
    }

    public int getQuantidadeDerrotas() {
        return quantidadeDerrotas;
    }

    public void setQuantidadeDerrotas(int quantidadeDerrotas) {
        this.quantidadeDerrotas = quantidadeDerrotas;
    }

    public int getQuantidadeEmpates() {
        return quantidadeEmpates;
    }

    public void setQuantidadeEmpates(int quantidadeEmpates) {
        this.quantidadeEmpates = quantidadeEmpates;
    }

    public BigDecimal getTotalKdr() {
        BigDecimal resultado;
        BigDecimal eliminacoes = new BigDecimal(this.totalQtdEliminacoes);
        BigDecimal baixas = new BigDecimal(this.totalQtdBaixas);
        resultado = eliminacoes.divide(baixas, 2, RoundingMode.HALF_UP);
        return resultado;
    }

    public int getPosicaoJogador() {
        return posicaoJogador;
    }

    public void setPosicaoJogador(int posicaoJogador) {
        this.posicaoJogador = posicaoJogador;
    }

    public void setTotalKdr(BigDecimal totalKdr) {
        this.totalKdr = totalKdr;
    }

}
