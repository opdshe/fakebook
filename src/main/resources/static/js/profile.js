let memberId = getUrlParameterByName('memberId')
let stompClient = null;
let profile = null;

async function initProfile() {
    await getLoginUserName()
        .then(function (response) {
            profile.user = response.data
        }).catch(function (error) {
            alert(error.response.data.message)
        })
    await getMemberByMemberId(memberId)
        .then(function (response) {
            profile.target = response.data
        }).catch(function (error) {
            alert(error.response.data.message)
        })
    hideChatTabIfUserIsTarget();
}

function initProfilePosts() {
    axios.get('/posts/' + memberId)
        .then(function (response) {
            console.log(response.data)
            profile.posts = response.data
        }).catch(function (error) {
        alert(error.response.data.message);
    });
}

async function getMemberByMemberId(memberId) {
    return await axios.get('/member/' + memberId);
}

function hideChatTabIfUserIsTarget() {
    if (profile.user.name === profile.target.name) {
        console.log("hide")
        console.log("user : " + profile.user.name + ", target :" + profile.target.name);
        $('#chat-tab').hide();
    } else {
        console.log("show")
        console.log("user : " + profile.user.name + ", target :" + profile.target.name);
        $('#chat-tab').show();
    }
}

async function initChat() {
    await axios.post('/create-chatroom/' + profile.target.id)
        .then(function (response) {
            console.log("채팅방 생성 성공");
        }).catch(function (error) {
            alert(error.response.data.message);
        });
    await axios.get('/fetch-messages/' + profile.target.id)
        .then(function (response) {
            profile.messages = response.data;
            console.log(response.data);
        }).catch(function (error) {
            console.log(error.response.data.message);
        })
}

async function init() {
    await initProfile()
    await initProfilePosts()
    await initChat()
}

profile = new Vue({
    el: '#fakebook-app',
    data: {
        target: '',
        posts: '',
        user: '',
        messages: ''
    }
})
init()

let stomp = {
    connect: function () {
        let _this = this;
        _this.init();
        let socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/queue/' + profile.user.id, function (chatMessage) {
                _this.showMessage(JSON.parse(chatMessage.body));
            });
        });
    },
    init: function () {
        let _this = this;
        $("form").on('submit', function (e) {
            e.preventDefault()
        });
        $("#send").click(function () {
            _this.send();
        });
    },
    send: function () {
        let _this = this;
        let content = $('#message').val();
        let message = {
            'senderId': profile.user.id,
            'receiverId': profile.target.id,
            'content': content,
        };
        stompClient.send("/app/send", {}, JSON.stringify(message));
        _this.showMessage(message);
    },
    showMessage: function (message) {
        let receiver = message.receiverId
        let sender = message.senderId
        if (!((receiver === profile.user.id && sender === profile.target.id) ||
            receiver === profile.target.id && sender === profile.user.id)) {
            return
        }
        if (profile.user.id !== receiver) {
            $("#messages").append("<div style=\"width: 100%; display: inline-block\">" +
                "<div  class=\"my-chat\"><span class=\"chat-message\" style=\"float: right;\">" + message.content + "</span>" +
                "</div></div>");
        } else {
            $("#messages").append("<div style=\"width: 100%; display: inline-block\">" +
                "<div  class=\"your-chat\"><span class=\"chat-message\" style=\"float: left;\">" + message.content + "</span>" +
                "</div></div>");
        }

    }
}
stomp.connect();


