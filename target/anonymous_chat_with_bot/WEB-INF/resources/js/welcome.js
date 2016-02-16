$(document).ready(function() {
    var $buttonRegister = $("#button-register");
    $buttonRegister.button();
    $buttonRegister.click(function(e) {
        e.preventDefault();
        $.get("/resources/html_templates/register_form.html", function(data) {
            $("#welcome").append(data);
        });
    });
    var $buttonLogin = $("#button-login");
    $buttonLogin.button();
    $buttonLogin.click(function(e) {
        e.preventDefault();
        $.get("/resources/html_templates/login_form.html", function(data) {
            $("#welcome").append(data);
        });
    });
});