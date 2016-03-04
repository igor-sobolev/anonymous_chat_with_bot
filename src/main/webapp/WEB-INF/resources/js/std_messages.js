var showMessages = function() {
    var $strategies = $("#strategies");
    var id = $strategies.val();
    if (id == -1) {
        return;
    }
    $strategies.prop("disabled", true);
    $("#strategy-messages-hide").prop("disabled", false);
    $("#strategy-messages").prop("disabled", true);

    $("#strategy-add").prop("disabled", true);
    $("#strategy-edit").prop("disabled", true);
    $("#strategy-remove").prop("disabled", true);
    $.ajax({
        url: "/bot/strategy/messages/" + id,
        type: "GET",
        dataType: "json",
        success: function(callback) {
            var html = "<table class=\"table table-hover\">";
            html += "<tr><th>Сообщение</th><th></th></tr>";
            $.each(callback, function(index, value) {
                html += "<tr><td>" + value.message + "</td>";
                html += "<td style=\"text-align: right; width: 35%\">";
                html += "<button onclick=\"editMessage(" + value.id + ")\"";
                html += " class=\"btn btn-default\"";
                html += "id=\"but-" + value.id + "\"><span class=\"glyphicon glyphicon-edit\"></span></button>";
                html += "<button onclick=\"deleteMessage(" + value.id + ")\"";
                html += " class=\"btn btn-default\"";
                html += "id=\"but-" + value.id + "\"><span class=\"glyphicon glyphicon-remove\"></span></button></td>";
                html += "</tr>";
            });
            $("#for-strategy-form").html(html);
        },
        error: function() {
            console.log("error save strategy");
        }
    });
};

var hideMessages = function() {
    $("#strategy-add").prop("disabled", false);
    $("#strategy-edit").prop("disabled", false);
    $("#strategy-remove").prop("disabled", false);
    $("#strategies").prop("disabled", false);
    $("#for-strategy-form").html("");
    $("#strategy-messages-hide").prop("disabled", true);
    $("#strategy-messages").prop("disabled", false);
};