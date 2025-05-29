$(document).ready(function () {

    $('#tabelaRank th').each(function(columnIndex) {
        $(this).css('cursor', 'pointer').click(function() {
            var table = $(this).parents('table').eq(0);
            var tbody = table.find('tbody');
            var rows = tbody.find('tr').toArray();

            var asc = !$(this).hasClass('asc');
            table.find('th').removeClass('asc desc');
            $(this).addClass(asc ? 'asc' : 'desc');

            rows.sort(function(a, b) {
                var cellA = $(a).children('td').eq(columnIndex).text().trim();
                var cellB = $(b).children('td').eq(columnIndex).text().trim();

                var valA = $.isNumeric(cellA.replace(",", ".")) ? parseFloat(cellA.replace(",", ".")) : cellA.toLowerCase();
                var valB = $.isNumeric(cellB.replace(",", ".")) ? parseFloat(cellB.replace(",", ".")) : cellB.toLowerCase();

                if (valA < valB) return asc ? -1 : 1;
                if (valA > valB) return asc ? 1 : -1;
                return 0;
            });

            $.each(rows, function(index, row) {
                tbody.append(row);
            });
        });
    });

});