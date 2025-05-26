$(document).ready(function () {

    if ($('#filterJogador').val() !== "") {
        let nome = $('#filterJogador').find(":selected").text();
        $('#nomeJogador').append(nome);
    }

});