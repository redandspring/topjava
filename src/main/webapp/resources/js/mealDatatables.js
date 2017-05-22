var ajaxUrl = 'ajax/meals/';
var datatableApi;

function clearFilter() {
    $("#filterMealForm").find('input')
        .not(':button, :submit, :reset, :hidden')
        .val('');
    updateTable();
}

function updateTable() {
    $.ajax({
        url: ajaxUrl + "filter?" + $("#filterMealForm").serialize(),
        type: 'GET',
        success: function (data) {
            datatableApi.clear().rows.add(data).draw();
            successNoty('Filtered');
        }
    });
    return false;
}

// $(document).ready(function () {
$(function () {

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