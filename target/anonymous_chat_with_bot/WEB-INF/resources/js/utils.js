function getJSON($form) {                                                       //serialize form to json
    var jsonData = {};
    var formData = $form.serializeArray();
    $.each(formData, function() {
        if (jsonData[this.name]) {
            if (!jsonData[this.name].push) {
                jsonData[this.name] = [jsonData[this.name]];
            }
            jsonData[this.name].push(this.value || '');
        } else {
            jsonData[this.name] = this.value || '';
        }

    });
    return jsonData;
}
var warningMessage = function(message) {                                       //create warning message with jquery-ui
    var html = "";
    html += "<div class=\"ui-state-highlight ui-corner-all\" style=\"margin-top: 20px; padding: 0 .7em;\">";
    html += "<p><span class=\"ui-icon ui-icon-info\" style=\"float: left; margin-right: .3em;\"></span>";
    html += message + "</p>";
    html += "</div>";
    return html;
};

var successMessage = function(message) {
    var html = "";
    html += "<p class=\"bg-success panel-body text-success\">"
        + message + "</p>";
    return html;
};

var errorMessage = function(message) {                                         //error message with jquery-ui
    var html = "";
    html += "<div class=\"ui-state-error ui-corner-all\" style=\"padding: 0 .7em;\">";
    html += "<p><span class=\"ui-icon ui-icon-alert\" style=\"float: left; margin-right: .3em;\"></span>";
    html += message + "</p>";
    html += "</div>";
    return html;
};

var delayMilisecondsFromLength = function (x) {
    if (x < 0) return 1000;
    if (x < 4) return 2000;
    if (x < 25) {
        return x / 4 * 1000;
    } else {
        return (Math.log(x - 22) / Math.log(2.45) + 5) * 1000;
    }
}