package dtos;

import models.Jogador;
import models.RegistroJogador;
import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistroJogadorDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "Selecione um jogador válido")
    private Jogador jogador;

    @Constraints.Required(message = "A quantidade de eliminações é obrigatório.")
    private int qtdEliminacoes;

    @Constraints.Required(message = "A quantidade de baixas é obrigatório.")
    private int qtdBaixas;

    @Constraints.Required(message = "A quantidade de dano é obrigatório.")
    private int qtdDano;

    @Constraints.Required(message = "A porcentagem de HS é obrigatório.")
    private int porcetagemHS;

    private Calendar dataCadastro;
    private Calendar dataAlteracao;

    public static RegistroJogadorDTO converterRegistroJogadorDTO(RegistroJogador registroJogador) {

        RegistroJogadorDTO registroJogadorDTO = new RegistroJogadorDTO();

        //TODO implementar

        return registroJogadorDTO;

    }

    public Calendar getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Calendar dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public Calendar getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Calendar dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public int getPorcetagemHS() {
        return porcetagemHS;
    }

    public void setPorcetagemHS(int porcetagemHS) {
        this.porcetagemHS = porcetagemHS;
    }

    public int getQtdDano() {
        return qtdDano;
    }

    public void setQtdDano(int qtdDano) {
        this.qtdDano = qtdDano;
    }

    public int getQtdBaixas() {
        return qtdBaixas;
    }

    public void setQtdBaixas(int qtdBaixas) {
        this.qtdBaixas = qtdBaixas;
    }

    public int getQtdEliminacoes() {
        return qtdEliminacoes;
    }

    public void setQtdEliminacoes(int qtdEliminacoes) {
        this.qtdEliminacoes = qtdEliminacoes;
    }

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    @Override
    public List<ValidationError> validate() {

        List<ValidationError> errors = new ArrayList<>();

        //TODO implementar validar todos os campos ou colocar uma annotation

        if (qtdEliminacoes < 0) {
            errors.add(new ValidationError(Scala.toSeq(List.of("qtdEliminacoes")), Scala.toSeq(List.of("Quantidade de eliminações devem ser maior que zero."))));
        }

        return errors.isEmpty() ? null : errors;

    }

}
