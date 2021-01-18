//상단 네비바
Vue.component('upper-nav-bar', {
    props: ['username'],
    template:
        '<header id="header" class="header navbar">\n' +
        '    <div class="header-container max-width-lg mx-auto">\n' +
        '        <ul class="nav-left mrg-left-0">\n' +
        '            <li>\n' +
        '                <a href="feed.html">\n' +
        '                    <div class="logo" style="background-image: url(\'/images/default/icons.png\')"></div>\n' +
        '                </a>\n' +
        '            </li>\n' +
        '            <li class="search-container">\n' +
        '            <span class="search">\n' +
        '                <form action="" method="get">\n' +
        '                    <input type="text" class="searchbox" placeholder="검색">\n' +
        '                    <button type="submit" class="search-btn">\n' +
        '                        <i class="ti-search"></i>\n' +
        '                    </button>\n' +
        '                </form>\n' +
        '            </span>\n' +
        '            </li>\n' +
        '        </ul>\n' +
        '        <ul class="nav-right">\n' +
        '            <li class="user-profile">\n' +
        '                <a>\n' +
        '                    <img class="profile-img img-fluid" src="/images/default/profile-default.png" alt="">\n' +
        '                    <div class="user-info mrg-left-5">\n' +
        '                        <span class="name pdd-right-5 text-white text-bold">{{username}}</span>\n' +
        '                    </div>\n' +
        '                </a>\n' +
        '            </li>\n' +
        '            <li class="">\n' +
        '                <a class="pointer">\n' +
        '                    <span class="text-white text-bold font-size-12">홈</span>\n' +
        '                </a>\n' +
        '            </li>\n' +
        '            <li>\n' +
        '                <a class="pointer">\n' +
        '                    <span class="text-white text-bold font-size-12">만들기</span>\n' +
        '                </a>\n' +
        '            </li>\n' +
        '            <li>\n' +
        '                <div class="friend-btn pointer mrg-vertical-10 mrg-horizon-5"></div>\n' +
        '            </li>\n' +
        '            <li>\n' +
        '                <div class="message-btn pointer mrg-vertical-10 mrg-horizon-5"></div>\n' +
        '            </li>\n' +
        '            <li>\n' +
        '                <div class="notification-btn pointer mrg-vertical-10 mrg-horizon-5"></div>\n' +
        '            </li>\n' +
        '        </ul>\n' +
        '    </div>\n' +
        '</header>',
})


//게시글 객체
Vue.component('post', {
    methods: {
        deletePost: function (postId) {
            axios.delete("/post/delete/" + postId)
                .then(function (response) {
                    console.log("delete post . id= " + response);
                    alert("게시물을 삭제했습니다. ");
                    window.location.reload();
                }).catch(function (error) {
                console.log(error);
            })
        }
    },
    props: ['post'],
    template:
        '<div class="card widget-feed padding-15">\n' +
        '    <div class="feed-header">\n' +
        '        <ul class="list-unstyled list-info">\n' +
        '            <li>\n' +
        '                <img class="thumb-img img-circle" src="/images/default/profile-default.png" alt="">\n' +
        '                <div class="info">\n' +
        '                    <a href="" class="title no-pdd-vertical text-semibold inline-block">{{post.poster}}</a>\n' +
        '                    <span></span>\n' +
        '                    <span class="sub-title">15시간</span>\n' +
        '                    <a class="pointer absolute top-0 right-0" data-toggle="dropdown"\n' +
        '                       aria-expanded="false">\n' +
        '                        <span class="btn-icon text-dark">\n' +
        '                            <i class="ti-more font-size-16"></i>\n' +
        '                        </span>\n' +
        '                    </a>\n' +
        '                    <ul class="dropdown-menu">\n' +
        '                        <li>\n' +
        '                            <a class="pointer" v-on:click="deletePost(post.id)">\n' +
        '                                <i class="ti-trash pdd-right-10 text-dark"></i>\n' +
        '                                <span class="">게시글 삭제</span>\n' +
        '                            </a>\n' +
        '                        </li>\n' +
        '                    </ul>\n' +
        '                </div>\n' +
        '            </li>\n' +
        '        </ul>\n' +
        '   </div>\n' +
        '   <div class="feed-body no-pdd">\n' +
        '       <div dir="auto" style="text-align:start">{{post.content}}</div>\n' +
        '   </div>\n' +
        '   <ul class="feed-action pdd-btm-5 border bottom">\n' +
        '       <li>\n' +
        '           <i class="fa fa-thumbs-o-up text-info font-size-16 mrg-left-5"></i>\n' +
        '           <span class="font-size-14 lh-2-1">67</span>\n' +
        '       </li>\n' +
        '       <li class="float-right">\n' +
        '           <span class="font-size-13">공유 78회</span>\n' +
        '       </li>\n' +
        '       <li class="float-right mrg-right-15">\n' +
        '           <span class="font-size-13">댓글 2개</span>\n' +
        '       </li>\n' +
        '   </ul>\n' +
        '   <ul class="feed-action border bottom d-flex">\n' +
        '       <li class="text-center flex-grow-1">\n' +
        '           <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">\n' +
        '               <i class="fa fa-thumbs-o-up font-size-16"></i>\n' +
        '               <span class="font-size-13">좋아요</span>\n' +
        '           </button>\n' +
        '       </li>\n' +
        '       <li class="text-center flex-grow-1">\n' +
        '           <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">\n' +
        '               <i class="fa fa-comment-o font-size-16"></i>\n' +
        '               <span class="font-size-13">댓글</span>\n' +
        '           </button>\n' +
        '       </li>\n' +
        '       <li class="text-center flex-grow-1">\n' +
        '           <button class="btn btn-default no-border pdd-vertical-0 no-mrg width-100">\n' +
        '               <i class="fa fa-share font-size-16"></i>\n' +
        '               <span class="font-size-13">공유하기</span>\n' +
        '           </button>\n' +
        '       </li>\n' +
        '   </ul>\n' +
        '   <div class="feed-footer">\n' +
        '       <div class="comment">\n' +
        '           <ul class="list-unstyled list-info">\n' +
        '               <li class="comment-item">\n' +
        '                   <img class="thumb-img img-circle" src="/images/default/eastjun_profile.jpg" alt="">\n' +
        '                   <div class="info">\n' +
        '                       <div class="bg-lightgray border-radius-18 padding-10 max-width-100">\n' +
        '                           <a href="" class="title text-bold inline-block text-link-color">eastjun</a>\n' +
        '                           <span>크 멋져요. MVC패턴을 직접 프로젝트에 적용해봤나요?</span>\n' +
        '                   </div>\n' +
        '                   <div class="font-size-12 pdd-left-10 pdd-top-5">\n' +
        '                       <span class="pointer text-link-color">좋아요</span>\n' +
        '                       <span>·</span>\n' +
        '                       <span class="pointer text-link-color">답글 달기</span>\n' +
        '                       <span>·</span>\n' +
        '                       <span class="pointer">2시간</span>\n' +
        '                   </div>\n' +
        '               </div>\n' +
        '           </li>\n' +
        '       </ul>\n' +
        '       <div class="add-comment">\n' +
        '                <textarea rows="1" class="form-control" placeholder="댓글을 입력하세요.."></textarea>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </div>\n' +
        '</div>'
})

//게시물 작성 카드
Vue.component('post-register-card', {
    props: ['placeholder'],
    data: function () {
        return {
            postContent: ''
        }
    },
    template:
        '<div class="card widget-compose" id="post-card">\n' +
        '    <p class="border bottom width-100 pdd-btm-5 text-bold">게시물 만들기</p>\n' +
        '    <textarea class="resize-none form-control border bottom resize-none"' +
        '       v-bind:placeholder="placeholder" v-model="postContent"></textarea>\n' +
        '    <ul class="composor-tools pdd-top-15">\n' +
        '        <li class="bg-lightgray border-radius-round mrg-right-5">\n' +
        '            <a class="pdd-vertical-5 pdd-horizon-10 pointer">\n' +
        '                <div class="icons photo-video"></div>\n' +
        '                <span class="icon-name font-size-13 text-bold"> 사진/동영상</span>\n' +
        '            </a>\n' +
        '        </li>\n' +
        '        <li class="bg-lightgray border-radius-round mrg-right-5">\n' +
        '            <a class="pdd-vertical-5 pdd-horizon-10 pointer">\n' +
        '                <div class="icons tag-friend"></div>\n' +
        '                <span class="icon-name font-size-13 text-bold"> 친구 태그하기</span>\n' +
        '            </a>\n' +
        '        </li>\n' +
        '        <li class="bg-lightgray border-radius-round mrg-right-5">\n' +
        '            <a class="pdd-vertical-5 pdd-horizon-10 pointer">\n' +
        '                <div class="icons feeling-activity"></div>\n' +
        '                <span class="icon-name font-size-13 text-bold"> 기분/활동</span>\n' +
        '            </a>\n' +
        '        </li>\n' +
        '        <input id="btn-post-register" type="submit" value="게시" v-on:click="register">\n' +
        '    </ul>\n' +
        '</div>',
    methods: {
        register: function (e) {
            axios.post("/post/register", {
                content: this.postContent
            }).then(function (response) {
                console.log(response);
                alert("게시물을 작성했습니다. ");
                window.location.reload();
            }).catch(function (error) {
                console.log(error);
            });
        }
    }
})

