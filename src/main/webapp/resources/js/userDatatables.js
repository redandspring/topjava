var ajaxUrl = 'ajax/admin/users/';
var datatableApi;

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear();
        $.each(data, function (key, item) {
            datatableApi.row.add(item);
        });
        datatableApi.draw();
    });
}

// $(document).ready(function () {
$(function () {

    $("body").on("change", "input.enabled", function(){
        $.ajax({
            url: ajaxUrl + "enabled/" + $(this).attr("id"),
            type: 'POST',
            success: function () {
                updateTable();
                successNoty('User Enabled');
            }
        });
    });

    datatableApi = $('#datatable').DataTable({
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "name"
            },
            {
                "data": "email"
            },
            {
                "data": "roles"
            },
            {
                "data": "enabled"
            },
            {
                "data": "registered"
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