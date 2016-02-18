$(document).ready(function() {
    $("#submit-login").button();
    $("#login-dialog").dialog({
        title: "Вход",
        modal: true,
        resizable: false,
        draggable: false,
        close: function() {
            $(this).dialog("destroy").remove();
        },
        buttons: [
            {
                text: "Ok",
                click: function() {
                    var data = getJSON($("#login-form"));
                    if ((data.login == "") || (data.password == "")) {          //empty fields
                        var html = warningMessage("Заполните все поля!");
                        $("#message").html(html);
                    } else {
                        $.ajax({                                                //check user
                            url: "/users/login",
                            type: "POST",
                            dataType: "json",
                            data: data,
                            success: function(callback) {
                                html = "";
                                if (callback) {
                                    storage.userLogin = callback.login;
                                    storage.score = callback.score;
                                    storage.email = callback.email;
                                    $("#login-dialog").dialog("close");
                                    $.get("/resources/html_templates/chat_form.html", function(data) {
                                        $("#main-window").html(data);
                                    });
                                } else {
                                    html = errorMessage("Неправильный логин или пароль!");
                                    $("#message").html(html);
                                }
                            },
                            error: function() {
                                console.log("error");
                                var html = errorMessage("Ошибка сервера!");
                                $("#message").html(html);
                            }
                        });
                    }
                }
            }
        ]
    });
});