$(document).ready(function() {
    $("#loading-dialog").dialog({                                               //no-closable loading dialog
        title: "Подключение к чату...",
        dialogClass: "no-close",
        modal: true,
        resizable: false,
        draggable: false,
        width: "300",
        height: "300",
        closeOnEscape: false,
        close: function() {
            $(this).dialog("destroy").remove();
        }
    });

    var user = {
        login: storage.userLogin
    };
    $.ajax({                                                                    //entering chatroom
        url: "/chat/enter",
        type: "POST",
        dataType: "json",
        data: user,
        success: function(cospeaker) {
            storage.chatId = cospeaker.chat.id;
            getCospeaker();
        },
        error: function() {
            console.log("error");
            var html = errorMessage("Ошибка сервера!");
            $("#message").html(html);
        }
    });

    var getCospeaker = function() {                                             //waiting for cospeaker
        var getCospeakerInterval = setInterval(function() {
            var chat = {
                chatId: storage.chatId
            };
            $.ajax({
                url: "/chat/cospeaker/entered",
                type: "POST",
                dataType: "json",
                data: chat,
                success: function(callback) {                                   //close dialog and notify user
                    if (callback) {
                        var $window = $("#chat-window");
                        $window.text($window.val() + "\nX вошел!");
                        clearInterval(getCospeakerInterval);
                        storage.chatBegan = true;
                        $("#loading-dialog").dialog("close");
                    }
                },
                error: function() {
                    console.log("error");
                    var html = errorMessage("Ошибка сервера!");
                    $("#message").html(html);
                }
            });
        }, 3000);
    };
});