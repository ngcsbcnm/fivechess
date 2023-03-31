
import $ from 'jquery'

export default {

    state: {
        id: "",
        username: "",
        photo: "",
        is_login: false,
        token: "",
        description: "",
        nick: "",
        rating: "",
        is_ai_vs_human: false,
        color: "",
        updateInfo: false,
    },
    getters: {
    },
    mutations: {
        updateInfoStatus(state, st) {
            state.updateInfo = st;
        },
        updateColor(state, color) {
            state.color = color;
        },

        update_is_ai_vs_human(state, st) {
            state.is_ai_vs_human = st;
        },
        updateDescription(state, info) {
            state.nick = info.nick;
            state.description = info.description;
        },
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.nick = user.nick;
            state.description = user.description;
            state.is_login = user.is_login;
            state.rating = user.rating;
        },

        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.nick = "";
            state.description = "";
            state.is_login = false;
        }

    },
    actions: {
        login(context, data) {
            $.ajax({

                url: "https://app4450.acapp.acwing.com.cn/api/user/token/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password
                },
                success(resp) {
                    if (resp.msg === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success();
                    } else {
                        data.error();
                    }
                    //console.log(resp);
                },
                error() {

                    data.error();
                }

            })
            //console.log(data);
        },

        getinfo(context, data) {

            $.ajax({

                url: "https://app4450.acapp.acwing.com.cn/api/user/info/",
                type: 'get',

                headers: {
                    Authorization: "Bearer " + context.state.token,
                }
                ,
                success(resp) {
                    if (resp.msg === "success") {
                        context.commit("updateUser", {
                            ...resp,
                            is_login: true
                        });
                        data.success();
                    } else {
                        data.error();
                    }
                    //console.log(resp);
                },

                error(resp) {
                    console.log(resp);
                    data.error();
                }

            })

        },
        updateinfo(context, info) {

            $.ajax({

                url: "https://app4450.acapp.acwing.com.cn/api/user/update/info/",
                type: 'post',
                data: {
                    nick: info.nick,
                    description: info.description
                },
                headers: {
                    Authorization: "Bearer " + context.state.token,
                }
                ,
                success(resp) {
                    if (resp.msg === "success") {
                        context.commit("updateDescription", {
                            nick: info.nick,
                            description: info.description
                        });
                    }
                    //console.log(resp);
                },

                error(resp) {
                    console.log(resp);
                }

            })

        },
        logout(state) {
            localStorage.removeItem("jwt_token");
            state.commit("logout");
        }
    },
    modules: {
    }



}