$(document).ready(function() {
    $("#submit-login").click(function(event) {
        event.preventDefault();
        var data = getJSON($("#login-form"));
        $.ajax({
            url: "/admin/login",
            type: "POST",
            dataType: "json",
            data: data,
            success: function(callback) {
                var html = "";
                if (callback) {
                    storage.userLogin = callback.login;
                    $.get("/resources/html_templates/admin_page.html", function(data) {
                        $("#main-window").html(data);
                    });
                } else {
                    html = errorMessage("Нет доступа!");
                    $("#message").html(html);
                }
            },
            error: function() {
                console.log("error");
                var html = errorMessage("Ошибка сервера!");
                $("#message").html(html);
            }
        });
    });
});