
<template>

    <div class="container text-center">
        <div class="row">
            <div class="col-3">
                <div class="card">
                    <div class="card-body" v-if="$store.state.user.photo === null || $store.state.user.photo === ''">
                        <img class="card-img-top" align="middle" src="@/assets/images/sly.png">
                    </div>
                    <div class="card-body" v-else>
                        <img class="card-img-top" align="middle" :src="$store.state.user.photo">
                    </div>

                </div>
                <div>
                    <span class="username">
                        {{ $store.state.user.username }}
                    </span>

                </div>
            </div>


            <div class="col-3">


                <div class="loader" v-if="heart">
                    <div></div>
                </div>
                <div class="font" v-if="heart">匹配中</div>
            </div>

            <div class="col-3">
                <div class="card">
                    <div class="card-body"
                        v-if="$store.state.pk.opponentPhoto === null || $store.state.pk.opponentPhoto === ''">
                        <img class="card-img-top" align="middle" src="@/assets/images/sly.png">
                    </div>
                    <div class="card-body" v-else>
                        <img class="card-img-top" align="middle" :src="$store.state.pk.opponentPhoto">
                    </div>
                </div>
                <div v-if="$store.state.pk.opponentUsername !== ''">
                    <span class="username">
                        {{ $store.state.pk.opponentUsername }}
                    </span>

                </div>
                <div v-else> <span class="username">(你的对手)?????????</span></div>
            </div>

        </div>

    </div>

    <div class="btns">
        <button @click="begin_match" type="button"
            :class="matching_info == '匹配' ? 'btn btn-lg match-btn btn-warning' : 'btn btn-lg match-btn btn-danger'">{{
                matching_info
            }}</button>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <button @click="click_return" type="button" class="btn btn-primary btn-lg">返回</button>

    </div>



</template>


<script>

import { ref } from 'vue';
import { useStore } from 'vuex';
export default {
    setup() {
        let matching_info = ref("匹配");
        const store = useStore();
        let heart = ref(false);
        const begin_match = () => {
            if (matching_info.value === '匹配') {
                matching_info.value = '取消';
                store.state.pk.socket.send(JSON.stringify({
                    event: "start-matching",
                }));
                heart.value = true;
            } else {
                matching_info.value = '匹配';
                store.state.pk.socket.send(JSON.stringify({
                    event: "stop-matching",
                }));
                heart.value = false;
            }
            //console.log("lalalalla");
        }
        const click_return = () => {

            store.commit("updateStatus", "selecting");
            store.commit("updateOpponent", {
                username: '',
                photo: '',
            })
        }


        return {
            matching_info,
            begin_match,
            click_return,
            heart
        }
    }

}

</script>


<style scoped>
.container {
    margin: auto;
    margin-top: 15vh;
    width: 70%;
    margin-left: 300px;
}

img {
    height: 210px;
}

.select {
    text-align: center;
    margin: auto;
}

.username {
    color: white;
    font-size: 24px;
    font-weight: 100px;
}

.row {
    margin-left: 100px;
}



.btns {
    margin-left: 42%;
    margin-top: 5%;
}

.card {
    margin-bottom: 30px;

    cursor: pointer;
}

.loader {
    display: inline-block;
    position: relative;
    width: 80px;
    height: 80px;
    transform: rotate(45deg);
    transform-origin: 40px 40px;
    margin-top: 20%;
}

.font {
    font-size: 20px;
    font-weight: 100;
    color: white;
}

.loader div {
    top: 32px;
    left: 32px;
    position: absolute;
    width: 32px;
    height: 32px;
    background: red;
    animation: loader45 1.2s infinite cubic-bezier(0.215, 0.61, 0.355, 1);
}

.loader div:after,
.loader div:before {
    content: " ";
    position: absolute;
    display: block;
    width: 32px;
    height: 32px;
    background: red;
}

.loader div:before {
    left: -24px;
    border-radius: 50% 0 0 50%;
}

.loader div:after {
    top: -24px;
    border-radius: 50% 50% 0 0;
}

@keyframes loader45 {
    0% {
        transform: scale(0.95);
    }

    5% {
        transform: scale(1.1);
    }

    39% {
        transform: scale(0.85);
    }

    45% {
        transform: scale(1);
    }

    60% {
        transform: scale(0.95);
    }

    100% {
        transform: scale(0.9);
    }
}
</style>