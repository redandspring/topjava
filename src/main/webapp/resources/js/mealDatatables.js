var ajaxUrl = 'ajax/meals/';
var datatableApi;

// $(document).ready(function () {
$(function () {

    $("#filterMealForm").on("submit", function () {
        $.ajax({
            url: ajaxUrl + "filter?" + $(this).serialize(),
            type: 'GET',
            success: function (data) {
                datatableApi.clear();
                $.each(data, function (key, item) {
                    datatableApi.row.add(item);
                });
                datatableApi.draw();
                successNoty('Filtered');
            }
        });
       return false;
    });

    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime"
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "Edit",
                "orderable": false
            },
            {
                "defaultContent": "Delete",
                "orderable": false
            }
        ],
        "order": [
            [
                0,
                "asc"
            ]
        ]
    });
    makeEditable();
});

function reset() {
    updateTable();
}