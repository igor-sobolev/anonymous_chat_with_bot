var clearMsg = function() {
    setTimeout(function() {
        $("#for-strategy-message").html("");
    }, 3000);
};
var loadStrategies = function() {
    $("#for-strategy-form").html("");
    $.ajax({
        url: "/bot/strategy",
        type: "GET",
        dataType: "json",
        success: function(callback) {
            var html = "<select id=\"strategies\" class=\"selection-handle\">"
                + "<option value=\"-1\" selected>Выберите стратегию</option>"
                + "</select>";
            $("#select").html(html);
            $.each(callback, function(index, value) {
                $("#strategies")
                    .append($("<option></option>")
                        .attr("value", value.id)
                        .text(value.name));
            });
        },
        error: function() {
            console.log("error loading strategies");
        }
    });
};

var saveStrategy = function() {
    var data = getJSON($("#strategy-form"));
    data.semantic = $("#semantic").prop("checked");
    data.crowdSource = $("#crowd-source").prop("checked");
    data.repeat = $("#repeat").prop("checked");
    if (!data.name) {
        $("#for-strategy-message").html(warningMessage("Задайте имя стратегии!"));
        clearMsg();
        return;
    }
    $.ajax({
        url: "/bot/strategy/",
        type: "POST",
        data: data,
        dataType: "json",
        success: function(callback) {
            var html = successMessage("стратегия " + callback.name + " сохранена");
            $("#for-strategy-message").html(html);
            clearMsg();
            loadStrategies();
            $("#for-strategy-form").html("");
        },
        error: function() {
            console.log("error save strategy");
        }
    });
};

var updateStrategy = function() {
    var id = $("#strategies").val();
    if (id == -1) {
        $("#for-strategy-form").html("");
        return;
    }
    $.ajax({
        url: "/bot/strategy/" + id,
        type: "GET",
        dataType: "json",
        success: function(callback) {
            $("#strategy-id").val(callback.id);
            $("#strategy-name").val(callback.name);
            $("#silence-prob").val(callback.silenceProb * 100);
            $("#msg-limit").val(callback.msgLimit);
            $("#unique").val(callback.unique);
            $("#error-prob").val(callback.errorProb * 100);
            $("#tanimoto-threshold").val(callback.tanimotoThreshold * 100);
            $("#crowd-rand").val(callback.crowdRand * 100);
            $("#initiative").val(callback.initiative * 100);
            if (callback.semantic) $("#semantic").prop("checked", true);
            if (callback.repeat) {
                $("#repeat").prop("checked", true);
                $("#unique").prop("disabled", false);
            }
            if (callback.crowdSource) $("#crowd-source").prop("checked", true);
        },
        error: function() {
            console.log("error update strategy");
        }
    });
};

var deleteStrategy = function() {
    var id = $("#strategies").val();
    if (id == -1) {
        $("#for-strategy-form").html("");
        return;
    }
    $.ajax({
        url: "/bot/strategy/" + id,
        type: "DELETE",
        dataType: "json",
        success: function(callback) {
            var html = successMessage("Стратегия " + callback.name + " удалена.")
            $("#for-strategy-message").html(html);
            clearMsg();
            loadStrategies();
        },
        error: function() {
            console.log("error delete strategy");
        }
    });
};