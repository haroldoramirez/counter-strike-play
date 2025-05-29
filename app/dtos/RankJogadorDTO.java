package dtos;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RankJogadorDTO {

    private String nome;
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
    private int totalQuantidadePartidas;

    public int getInimigosCegosPorGame() {
        return totalQtdInimigosCegos/totalQuantidadePartidas;
    }

    public int getDanoUtilPorGame() {
        return totalQtdDanoUtilitario/totalQuantidadePartidas;
    }

    public int getEliminacoesPorGame() {
        return totalQtdEliminacoes/totalQuantidadePartidas;
    }

    public int getDanoPorGame() {
        return totalQtdDano/totalQuantidadePartidas;
    }

    public int getTotalQuantidadePartidas() {
        return totalQuantidadePartidas;
    }

    public void setTotalQuantidadePartidas(int totalQuantidadePartidas) {
        this.totalQuantidadePartidas = totalQuantidadePartidas;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public BigDecimal getTotalKdr() {
        BigDecimal resultado;
        BigDecimal eliminacoes = new BigDecimal(this.totalQtdEliminacoes);
        BigDecimal baixas = new BigDecimal(this.totalQtdBaixas);
        resultado = eliminacoes.divide(baixas, 2, RoundingMode.HALF_UP);
        return resultado;
    }

    public BigDecimal getPorcentagemVitoria() {

        int quantidadeVitorias = getQuantidadeVitorias();
        int totalQuantidadePartidas = getTotalQuantidadePartidas();

        if (totalQuantidadePartidas == 0) {
            System.out.println("Não é possível dividir por zero.");
            return BigDecimal.valueOf(0);
        }

        BigDecimal vitorias = new BigDecimal(quantidadeVitorias);
        BigDecimal partidas = new BigDecimal(totalQuantidadePartidas);

        BigDecimal percentualVitoria = vitorias
                .divide(partidas, 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .setScale(2, RoundingMode.HALF_UP);

       return percentualVitoria;

    }

}
