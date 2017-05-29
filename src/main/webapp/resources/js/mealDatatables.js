var ajaxUrl = "ajax/profile/meals/";
var datatableApi;

function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize(),
        success: function(data){
            updateTableByData(data);
        }
    });
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {

    $.datetimepicker.setLocale("ru");
    $("#dateTime").datetimepicker({
        format: "Y-m-d H:i"
    });

    $("#startDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d"
    });
    $("#endDate").datetimepicker({
        timepicker: false,
        format: "Y-m-d"
    });
    $("#startTime").datetimepicker({
        datepicker: false,
        format: "H:i"
    });
    $("#endTime").datetimepicker({
        datepicker: false,
        format: "H:i"
    });



    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === 'display') {
                        var exceedClass = (row.exceed) ? 'exceededNested' : 'normalNested';
                        return '<span class="'+exceedClass+'">' + date.replace("T", " ") + '</span>';
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderEditBtn
            },
            {
                "orderable": false,
                "defaultContent": "",
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
        "createdRow": function (row, data) {
            $(row).addClass(data.exceed ? 'exceeded' : 'normal');
        },
        "initComplete": makeEditable

    });

});