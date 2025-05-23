package dtos;

import models.RegistroJogador;
import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RegistroJogadorDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "Selecione um jogador válido.")
    @Constraints.Min(value = 1, message = "Selecione um jogador válido")
    private Long jogador;

    @Constraints.Required(message = "A quantidade de eliminações é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de eliminações válida.")
    private Integer qtdEliminacoes;

    @Constraints.Required(message = "A quantidade de baixas é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de baixas válido.")
    private Integer qtdBaixas;

    @Constraints.Required(message = "A quantidade de dano é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de dano válido.")
    private Integer qtdDano;

    @Constraints.Required(message = "A porcentagem de HS é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma porcentagem de HS válida.")
    private Integer porcetagemHS;

    @Constraints.Required(message = "Selecione se teve vitória ou derrota.")
    private Boolean vitoria;

    @Constraints.Required(message = "A quantidade de dano por utilitário é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de dano por utilitário válida.")
    private Integer qtdDanoUtilitario;

    @Constraints.Required(message = "A quantidade de inimigos cegos é obrigatório.")
    @Constraints.Min(value = 1, message = "Entre com uma quantidade de inimigos cegos válida.")
    private Integer qtdInimigosCegos;

    private String mapa;

    private Calendar dataCadastro;
    private Calendar dataAlteracao;

    /** Necessario para instanciar o form */
    public RegistroJogadorDTO() {}

    public Long getJogador() {
        return jogador;
    }

    public void setJogador(Long jogador) {
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

    public Integer getQtdInimigosCegos() {
        return qtdInimigosCegos;
    }

    public void setQtdInimigosCegos(Integer qtdInimigosCegos) {
        this.qtdInimigosCegos = qtdInimigosCegos;
    }

    public Integer getQtdDanoUtilitario() {
        return qtdDanoUtilitario;
    }

    public void setQtdDanoUtilitario(Integer qtdDanoUtilitario) {
        this.qtdDanoUtilitario = qtdDanoUtilitario;
    }

    public Boolean getVitoria() {
        return vitoria;
    }

    public void setVitoria(Boolean vitoria) {
        this.vitoria = vitoria;
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

    public static RegistroJogadorDTO converterRegistroJogadorRegistroJogadorDTO(RegistroJogador registroJogador) {

        RegistroJogadorDTO registroJogadorDTO = new RegistroJogadorDTO();

        registroJogadorDTO.setVitoria(registroJogador.getVitoria());
        registroJogadorDTO.setQtdDanoUtilitario(registroJogador.getQtdDanoUtilitario());
        registroJogadorDTO.setQtdInimigosCegos(registroJogador.getQtdInimigosCegos());
        registroJogadorDTO.setPorcetagemHS(registroJogador.getPorcetagemHS());
        registroJogadorDTO.setQtdBaixas(registroJogador.getQtdBaixas());
        registroJogadorDTO.setQtdDano(registroJogador.getQtdDano());
        registroJogadorDTO.setQtdEliminacoes(registroJogador.getQtdEliminacoes());

        return registroJogadorDTO;

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