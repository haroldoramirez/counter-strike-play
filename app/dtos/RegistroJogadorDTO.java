package dtos;

import models.RegistroJogador;
import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistroJogadorDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "Selecione um jogador válido")
    @Constraints.Min(value = 1, message = "Selecione um jogador válido")
    private Integer jogador;

    @Constraints.Required(message = "A quantidade de eliminações é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de eliminações válida")
    private Integer qtdEliminacoes;

    @Constraints.Required(message = "A quantidade de baixas é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de baixas válido")
    private Integer qtdBaixas;

    @Constraints.Required(message = "A quantidade de dano é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de dano válido")
    private Integer qtdDano;

    @Constraints.Required(message = "A porcentagem de HS é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma porcentagem de HS válida")
    private Integer porcetagemHS;

    private Calendar dataCadastro;
    private Calendar dataAlteracao;

    /** Necessario para instanciar o form */
    public RegistroJogadorDTO() {}

    public RegistroJogadorDTO(Calendar dataAlteracao, Calendar dataCadastro, Integer porcetagemHS, Integer qtdDano, Integer qtdBaixas, Integer qtdEliminacoes, Integer jogador) {
        this.dataAlteracao = dataAlteracao;
        this.dataCadastro = dataCadastro;
        this.porcetagemHS = porcetagemHS;
        this.qtdDano = qtdDano;
        this.qtdBaixas = qtdBaixas;
        this.qtdEliminacoes = qtdEliminacoes;
        this.jogador = jogador;
    }

    public Integer getJogador() {
        return jogador;
    }

    public void setJogador(Integer jogador) {
        this.jogador = jogador;
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

    public Integer getPorcetagemHS() {
        return porcetagemHS;
    }

    public void setPorcetagemHS(Integer porcetagemHS) {
        this.porcetagemHS = porcetagemHS;
    }

    public Integer getQtdDano() {
        return qtdDano;
    }

    public void setQtdDano(Integer qtdDano) {
        this.qtdDano = qtdDano;
    }

    public Integer getQtdBaixas() {
        return qtdBaixas;
    }

    public void setQtdBaixas(Integer qtdBaixas) {
        this.qtdBaixas = qtdBaixas;
    }

    public Integer getQtdEliminacoes() {
        return qtdEliminacoes;
    }

    public void setQtdEliminacoes(Integer qtdEliminacoes) {
        this.qtdEliminacoes = qtdEliminacoes;
    }

    public static RegistroJogador converterRegistroJogadorDTO(RegistroJogadorDTO registroJogadorDTO) {

        RegistroJogador registroJogador = new RegistroJogador();

        //TODO implementar

        return registroJogador;

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
