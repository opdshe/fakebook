initFeedData()
initPosts()


var feed = new Vue({
    el: '#fakebook-app',
    data: {
        username: '',
        placeholder: '',
        posts: ''
    }
})

function initFeedData() {
    axios.get('/user-name')
        .then(function (response) {
            console.log(response.data)
            feed.username = response.data
            feed.placeholder = feed.username + "님은 무슨 생각을 하고 계신가요?"
        }).catch(function (error) {
        alert(error.response.data.message);
    });
}

function initPosts() {
    axios.get('/posts')
        .then(function (response) {
            console.log(response.data)
            feed.posts = response.data
        }).catch(function (error) {
        alert(error.response.data.message);
    });
}
