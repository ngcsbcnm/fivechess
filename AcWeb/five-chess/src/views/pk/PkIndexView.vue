<template>

    <GameOverView v-if="$store.state.pk.result !== ''" />
    <AIPkResult v-if="$store.state.ai.result !== ''" />
    <AIVsPeople v-if="$store.state.pk.status === 'AIpk'" />
    <ModelSelectView v-if="$store.state.pk.status === 'selecting'" />

    <PlayGround v-if="$store.state.pk.status === 'userPK'" />
    <MatchingSystem v-if="$store.state.pk.status === 'matching'" />


</template>



<script>

import { onUnmounted, onMounted } from 'vue';

import PlayGround from './PlayGround.vue';
import { useStore } from 'vuex';
import ModelSelectView from './ModelSelectView.vue'
import AIVsPeople from './AIVsPeople.vue';
import MatchingSystem from '@/views/pk/MatchingSystem'
import GameOverView from '@/views/pk/GameOverView'
import AIPkResult from './AIPkResult.vue';
export default {

    components: {
        PlayGround,
        ModelSelectView,
        AIVsPeople,
        MatchingSystem,
        GameOverView,
        AIPkResult
    },
    setup() {
        const store = useStore();
        let socket = null;
        const socketUrl = `wss://app4450.acapp.acwing.com.cn/websocket/${store.state.user.token}/`;
        //console.log(socketUrl);
        onMounted(() => {
            socket = new WebSocket(socketUrl);

            socket.onopen = () => {
                console.log("onopen!");
                store.commit("updateSocket", socket);
            }

            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                //console.log("data      ", data);
                if (data.event === 'start-matching') {
                    if (data.opponent_photo === null) {
                        data.opponent_photo = 'https://assets.leetcode.cn/aliyun-lc-upload/users/faker-fs/avatar_1669443382.png?x-oss-process=image%2Fformat%2Cwebp';
                    }
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    })
                    setTimeout(() => {
                        store.commit("updateStatus", 'userPK');
                    }, 100);

                    if (data.color === 'black') {
                        store.commit("updateIsme", 1);

                        store.commit("updateContent", {
                            photo: 'https://cdn.acwing.com/media/user/profile/photo/1_md_844c66b332.jpg',
                            message: '哈喽！~ ！你执黑棋先手🧡💛💚💙💜，每回合只有十秒钟的思考时间哦！！',
                            who: 'he',
                            id: -1,
                        });

                    } else {
                        store.commit("updateContent", {
                            photo: 'https://i1.hdslb.com/bfs/face/9d556132929b64ea5334687c5af73e198f5f2127.jpg@96w_96h_1c.webp',
                            message: '嗨！~ ！你执白棋后手🧡💛💚💙💜，每回合只有十秒钟的思考时间哦！！',
                            who: 'he',
                            id: -2,
                        });
                    }
                    store.commit("updateColor", data.color);


                    //console.log("qweqweqweqweqweqweqweqweqweqweqweqw");

                } else if (data.event === 'move') {

                    store.state.pk.game.stack.push({
                        color: data.color,
                        i: parseInt(data.x),
                        j: parseInt(data.y)
                    });

                    store.commit("updateIsover", data.is_over);

                    if (store.state.user.color === 'black') {
                        if (data.is_over === 1) {
                            store.commit("updateResult", "恭喜你赢了！！！💜💙💛💚");
                        } else if (data.is_over === 2) {
                            store.commit("updateResult", "输了就输了咯~！💜💙💛💚");
                        }
                    } else {
                        if (data.is_over === 1) {
                            store.commit("updateResult", "输了就输了咯~💜💙💛💚");
                        } else if (data.is_over === 2) {
                            store.commit("updateResult", "恭喜你赢了！！！💜💙💛💚");
                        }
                    }

                } else if (data.event === 'send-message') {
                    store.commit("updateContent", {
                        photo: data.photo,
                        message: data.message,
                        who: data.who,
                        id: data.id,
                    });
                } else if (data.event === 'timeout') {
                    store.commit("updateIsover", 3);
                    if (data.loser === 'all') {
                        store.commit("updateResult", "平局了");
                    }
                    else if (data.loser === 'A') {
                        if (store.state.user.color === 'white') {
                            store.commit("updateResult", "恭喜你赢了！！！💜💙💛💚");
                        } else {
                            store.commit("updateResult", "输了就输了咯~💜💙💛💚");
                        }
                    } else {
                        if (store.state.user.color === 'black') {
                            store.commit("updateResult", "恭喜你赢了！！！💜💙💛💚");
                        } else {
                            store.commit("updateResult", "输了就输了咯~💜💙💛💚");
                        }
                    }

                    //console.log('timeout', data);
                }
            }
            socket.onclose = () => {
                console.log("onclose!");
                store.commit("updateOpponent", {
                    username: "",
                    photo: '',
                })
                store.commit("updateIsover", 0);
            }
        })


        onUnmounted(() => {
            socket.close();
        })



    }
}

</script>

<style scoped>

</style>