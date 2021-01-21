let memberId = getUrlParameterByName('memberId')

var profile = new Vue({
    el: '#fakebook-app',
    data: {
        user: '',
        posts: '',
        username: ''
    }
})

initProfile()
initProfilePosts()

function initProfile() {
    getLoginUserName()
        .then(function (response) {
            profile.username = response.data
        }).catch(function (error) {
        alert(error.response.data.message)
    })
    getMemberByMemberId(memberId)
        .then(function (response) {
            profile.user = response.data
        }).catch(function (error) {
        alert(error.response.data.message)
    })
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
