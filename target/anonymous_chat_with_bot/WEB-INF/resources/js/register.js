$(document).ready(function() {
    $("#submit-register").button();
    $("#register-dialog").dialog({
        title: "Регистрация",
        modal: true,
        resizable: false,
        draggable: false,
        width: "25%",
        close: function() {
            $(this).dialog("destroy").remove();
        },
        buttons: [
            {
                text: "Зарегистрироваться",
                click: function() {
                    var html;
                    var data = getJSON($("#register-form"));
                    if (!data.login || !data.email || !data.password || !data.password2) {//empty fields
                        html = warningMessage("Заполните все поля!");
                        $("#message").html(html);
                    } else if (data.password != data.password2) {//bad pass confirmation
                        html = warningMessage("Неправильное подтверждение пароля!");
                        $("#message").html(html);
                    } else {
                        $.ajax({                                                //check user for registration
                            url: "/users/register",
                            type: "POST",
                            dataType: "json",
                            data: data,
                            success: function(callback) {
                                var html = "";
                                if (callback) {
                                    $("#register-dialog").dialog("close");
                                    registerSuccess();
                                } else {
                                    html = errorMessage("Логин занят!");
                                    $("#message").html(html);
                                }
                            },
                            error: function() {
                                var html;
                                console.log("error");
                                html = errorMessage("Ошибка сервера!");
                                $("#message").html(html);
                            }
                        });
                    }
                }
            }
        ]
    });
    var registerSuccess = function() {
        var okDialog = "";
        okDialog += "<div id=\"ok-dialog\">";
        okDialog += "Регистрация прошла успешно!";
        okDialog += "</div>";
        $("#main-window").append(okDialog);
        $("#ok-dialog").dialog({
            draggable: false,
            resizable: false,
            modal: true,
            close: function() {
                $(this).dialog("destroy").remove();
            },
            buttons: [
                {
                    text: "Ok",
                    click: function() {
                        $("#ok-dialog").dialog("close");
                    }
                }
            ]
        });
    };
});