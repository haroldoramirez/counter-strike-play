$(document).ready(function () {
    document.addEventListener("DOMContentLoaded", function () {

        const url = window.location.pathname;

        // Verifique a URL desejada (exemplo: /jogador/sucesso)
        if (url === "/") {
            const meuModal = new bootstrap.Modal(document.getElementById('mainModal'));
            meuModal.show();
        }

    });
});