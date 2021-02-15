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

profile = new Vue({
    el: '#fakebook-app',
    data: {
        target: '',
        posts: '',
        user: ''
    }
})

initProfile()
initProfilePosts()

let stomp = {
    connect: function () {
        let _this = this;
        _this.init();
        let socket = new SockJS('/chat');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);
            stompClient.subscribe('/topic/public-message', function (greeting) {
                _this.receive(JSON.parse(greeting.body).content);
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
        let content = $('#message').val();
        stompClient.send("/app/hello", {}, JSON.stringify({'content': content}));
    },
    receive: function (message) {
        $("#messages").append("<tr><td>" + message + "</td></tr>");
    }
}

stomp.connect();


