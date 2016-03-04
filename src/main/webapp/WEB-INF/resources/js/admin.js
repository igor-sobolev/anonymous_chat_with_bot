$(document).ready(function() {
    $("#tabs").tabs();
    storage.crowdPage = 0;
    initCrowdSourcePage(storage.crowdPage);
    $("#next").click(function(event) {
        event.preventDefault();
        storage.crowdPage = (storage.crowdPage < storage.crowdPageCount - 1)
            ? storage.crowdPage + 1
            : storage.crowdPage;
        initCrowdSourcePage(storage.crowdPage);
        drawPages();
    });
    $("#prev").click(function(event) {
        event.preventDefault();
        initCrowdSourcePage(storage.crowdPage == 0 ? 0 : --storage.crowdPage);
        drawPages();
    });
    $("#add-button").click(function(event) {
        event.preventDefault();
        $.get("/resources/html_templates/ml_add_update.html", function(html) {
            $("#form-div").html(html);
            $("#submit-add").click(function(e) {
                e.preventDefault();
                saveMl();
            });
        });
    });
    setInterval(drawPages, 500);

    //--------------------------------------------strategies
    loadStrategies();
    $("#strategy-add").click(function(e) {
        e.preventDefault();
        $.get("/resources/html_templates/strategy_form.html", function(html) {
            $("#for-strategy-form").html(html);
            $("#strategy-save").click(function(e) {
                e.preventDefault();
                saveStrategy();
            });
        });
    });
    $("#strategy-edit").click(function(e) {
        e.preventDefault();
        $.get("/resources/html_templates/strategy_form.html", function(html) {
            $("#for-strategy-form").html(html);
            updateStrategy();
            $("#strategy-save").click(function(e) {
                e.preventDefault();
                saveStrategy();
            });
        });
    });
    $("#strategy-remove").click(function(e) {
        e.preventDefault();
        deleteStrategy();
    });

    $("#strategy-messages").click(function(e) {
        e.preventDefault();
        showMessages();
    });

    $("#strategy-messages-hide").click(function(e) {
        e.preventDefault();
        hideMessages();
    });

    //--------------------------std-messages

});