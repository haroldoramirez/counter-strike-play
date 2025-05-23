package models.enums;

public enum StatusPartida {

    DERROTA("Derrota"),
    VITORIA("Vitória"),
    EMPATE("Empate");

    private final String descricao;

    StatusPartida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}