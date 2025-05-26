package dtos;

public class EstatisticaJogadorDTO {

    private String nomeJogador;
    private String mapaMaisJogado;
    private int quantidadeVitorias;
    private int totalQtdEliminacoes;
    private int totalQtdBaixas;
    private int totalQtdDano;
    private int totalMaiorPorcetagemHS;
    private int totalQtdDanoUtilitario;
    private int totalQtdInimigosCegos;

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

}
