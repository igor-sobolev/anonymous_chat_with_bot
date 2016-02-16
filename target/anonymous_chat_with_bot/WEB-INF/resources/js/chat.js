$(document).ready(function() {
    storage.messages = [];                                                      //list of messages of current chat
    storage.maxId = 0;
    var $logoff = $("#logoff");
    var $sendMessage = $("#send-message");
    $logoff.button();                                                           //ui buttons
    $("#bot").button();
    $("#not").button();
    $sendMessage.button();
    $logoff.click(function() {                                                  //go to welcome form
        $.get("/resources/html_templates/welcome_form.html", function(data) {
            $("#main-window").html(data);
        });
        clearInterval(intervalForReceivingMessages);
    });
    var scrollToBottom = function() {                                           //scrolling for textarea
        var $c = $("#chat-window");
        $c.scrollTop($c[0].scrollHeight);
    };
    var send = function() {
        var $msg = $("#msg-text");                                              //input
        var msg = $msg.val();
        $msg.val("");                                                           //clear input
        if (!msg) return;
        var $window = $("#chat-window");
        $window.val($window.val() + "\n" + storage.userLogin + ": " + msg);    //add message to chat
        scrollToBottom();
        storage.messages.push({
            sender: "I",
            message: msg
        });
        var chosen = false;
        var stimulus;
        for (var i = storage.messages.length - 1; i > 0; i--) {
            if (!chosen && storage.messages[i].sender == "X") {
                chosen = true;
                stimulus = storage.messages[i].message;
            }
        }
        if (!chosen) {
            stimulus = "";
        }
        var messageForAjax = {
            chatId: storage.chatId,
            userLogin: storage.userLogin,
            stimulus: stimulus,                                                 //for crowdsourcing bot
            message: msg
        };
        $.ajax({
            url: "/chat/message",
            type: "POST",
            dataType: "text",
            data: messageForAjax,
            error: function() {
                console.log("error sending message");
            }
        });
    };
    $("#msg-text").bind("keypress", function(e) {                              //same function as click
        e = e || window.event;
        if (e.keyCode === 13) {
            send();
        }
    });
    $sendMessage.click(send);                                                   //click-send
    var html = "<p style=\"font-size: large; \">";
    html += "User: " + storage.userLogin;
    html += "</p>";
    $("#user-login").html(html);
    $.get("/resources/html_templates/chat_loading.html", function(data) {
        $("#main-window").append(data);
    });                                                                         //loading dialog and functionality
    var intervalForReceivingMessages = setInterval(function() {                 //receiving messages from server
        var chat = {
            userLogin: storage.userLogin,
            chatId: storage.chatId,
            maxMessageId: storage.maxId
        };
        $.ajax({
            url: "/chat/receive",
            type: "POST",
            dataType: "json",
            data: chat,
            success: function(list) {
                list.sort(function(a, b) {
                    return a.id - b.id;
                });
                $.each(list, function(index) {
                    if (storage.maxId < list[index].id) {
                        storage.maxId = list[index].id;
                    }
                    var msg = list[index].message;
                    var $window = $("#chat-window");
                    $window.val($window.val() + "\n" + "X: " + msg);    //add message to chat
                    storage.messages.push({
                        sender: "X",
                        message: msg
                    });
                });
                scrollToBottom();
            },
            error: function() {
                console.log("error receiving messages");
            }
        });
    }, 4000);
});