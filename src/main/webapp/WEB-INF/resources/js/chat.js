$(document).ready(function() {
    storage.messages = [];                                                      //list of messages of current chat
    storage.maxId = 0;
    storage.chatBegan = false;
    var $tabs = $("#tabs");
    var $logoff = $("#logoff");
    var $bot = $("#bot");
    var $nobot = $("#nobot");
    var $newChat = $("#new-chat");
    var $sendMessage = $("#send-message");
    $logoff.button();                                                           //ui buttons
    $bot.button();
    $nobot.button();
    $newChat.button();
    $sendMessage.button();
    $tabs.tabs();
    $logoff.click(function() {                                                  //go to welcome form
        $.get("/resources/html_templates/welcome_form.html", function(data) {
            $("#main-window").html(data);
        });
        clearInterval(intervalForReceivingMessages);
    });
    var fillUser = function() {
        $.ajax({
            url: "/users/get/" + storage.userLogin,
            type: "GET",
            dataType: "json",
            success: function(user) {
                var html = "<p style=\"font-size: large; \">";
                html += "Пользователь: " + user.login + " Счет: " + user.score;
                html += "</p>";
                $("#user-login").html(html);
            },
            error: function() {
                console.log("error refreshing user");
            }
        });
    };
    var getTop10 = function() {
        $.ajax({
            url: "/users/top",
            type: "GET",
            dataType: "json",
            success: function(list) {
                var html = "";
                html += "<table class=\"table table:hover\" height=\"90%\">";
                html += "<tr><th>№</th><th>Пользователь</th><th>Очки</th></tr>";
                $.each(list, function(index, value) {
                    html += "<tr><td>" + Number(index + 1)
                        + "</td><td>" + value.login + "</td><td>"
                        + Number(value.score) + "</td></tr>";
                });
                for (var i = list.length; i < 10; i++) {
                    html += "<tr><td>" + Number(i + 1)
                        + "</td><td>" + "Пусто" + "</td><td>"
                        + Number(0) + "</td></tr>";
                }
                html += "</table>";
                $("#top").html(html);
            },
            error: function() {
                console.log("error receiving top");
            }
        });
    };
    $bot.click(function() {                                                  //go to welcome form
        $bot.prop("disabled",true);
        $nobot.prop("disabled",true);
        var chat = {
            userLogin: storage.userLogin,
            chatId: storage.chatId
        };
        $.ajax({
            url: "/chat/bot",
            type: "POST",
            dataType: "json",
            data: chat,
            success: function(callback) {
                if (callback) {
                    $("#main-window").append("<div id=\"result\">Поздравляем! Вы совершенно правы!</div>");
                    $("#result").dialog({
                        title: "Победа",
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
                                    $("#result").dialog("close");
                                }
                            }
                        ]
                    });
                } else {
                    $("#main-window").append("<div id=\"result\">В следующий раз повезет!</div>");
                    $("#result").dialog({
                        title: "Проигрыш",
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
                                    $("#result").dialog("close");
                                }
                            }
                        ]
                    });
                }
                getTop10();
                fillUser();
            },
            error: function() {
                console.log("error resulting");
            }
        });
    });
    $nobot.click(function() {                                                  //go to welcome form
        $bot.prop("disabled",true);
        $nobot.prop("disabled",true);
        var chat = {
            userLogin: storage.userLogin,
            chatId: storage.chatId
        };
        $.ajax({
            url: "/chat/nobot",
            type: "POST",
            dataType: "json",
            data: chat,
            success: function(callback) {
                if (callback) {
                    $("#main-window").append("<div id=\"result\">Поздравляем! Вы совершенно правы!</div>");
                    $("#result").dialog({
                        title: "Победа",
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
                                    $("#result").dialog("close");
                                }
                            }
                        ]
                    });
                } else {
                    $("#main-window").append("<div id=\"result\">В следующий раз повезет!</div>");
                    $("#result").dialog({
                        title: "Проигрыш",
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
                                    $("#result").dialog("close");
                                }
                            }
                        ]
                    });
                }
                getTop10();
                fillUser();
            },
            error: function() {
                console.log("error resulting");
            }
        });
    });
    $newChat.click(function() {                                                  //go to welcome form
        $.get("/resources/html_templates/chat_form.html", function(data) {
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
    $.get("/resources/html_templates/chat_loading.html", function(data) {
        $("#main-window").append(data);
    });
    var intervalForReceivingMessages;                                           //loading dialog and functionality
    var receiveMessages = function() {
        intervalForReceivingMessages = setInterval(function() {                 //receiving messages from server
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
    };
    var checkForChatStarted = setInterval(function() {
        if (storage.chatBegan) {
            clearInterval(checkForChatStarted);
            receiveMessages();
        }
    }, 1000);
    getTop10();
    fillUser();
});