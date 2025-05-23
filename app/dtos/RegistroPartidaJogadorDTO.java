package dtos;

import models.RegistroPartidaJogador;
import play.api.data.validation.ValidationError;
import play.data.validation.Constraints;
import play.libs.Scala;

import java.util.ArrayList;
import java.util.List;

public class RegistroPartidaJogadorDTO implements Constraints.Validatable<List<ValidationError>> {

    @Constraints.Required(message = "Selecione um jogador válido.")
    @Constraints.Min(value = 1, message = "Selecione um jogador válido")
    private Long jogador;

    @Constraints.Required(message = "Selecione um mapa válido.")
    @Constraints.Min(value = 1, message = "Selecione um mapa válido")
    private Long mapa;

    @Constraints.Required(message = "Selecione um status da partida válido.")
    @Constraints.MaxLength(value = 8, message = "O status da partida deve ter no máximo 8 caracteres.")
    private String statusPartida;

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

    /** Necessario para instanciar o form */
    public RegistroPartidaJogadorDTO() {}

    public Long getJogador() {
        return jogador;
    }

    public void setJogador(Long jogador) {
        this.jogador = jogador;
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

    public Long getMapa() {
        return mapa;
    }

    public void setMapa(Long mapa) {
        this.mapa = mapa;
    }

    public String getStatusPartida() {
        return statusPartida;
    }

    public void setStatusPartida(String statusPartida) {
        this.statusPartida = statusPartida;
    }

    public static RegistroPartidaJogadorDTO converterRegistroJogadorRegistroJogadorDTO(RegistroPartidaJogador registroJogador) {

        RegistroPartidaJogadorDTO registroJogadorDTO = new RegistroPartidaJogadorDTO();

        registroJogadorDTO.setJogador(registroJogador.getJogador().getId());
        registroJogadorDTO.setMapa(registroJogador.getMapa().getId());
        registroJogadorDTO.setStatusPartida(registroJogador.getStatusPartida().name());

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