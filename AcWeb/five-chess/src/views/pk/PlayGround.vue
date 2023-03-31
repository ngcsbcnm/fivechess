<template>

    <div ref="parent" class="play">

        <canvas ref="ctx">

        </canvas>




    </div>

    <div class="card card-1">
        <div class="card-header">
            互动区
        </div>
        <div class="card-body">




            <div class="user-message" v-for="ms of $store.state.pk.content" :key="ms.id">

                <img v-if="ms.id === -1" src="@/assets/images/yxc.png" alt=""
                    :class="ms.who === 'me' ? 'img-fluid self' : 'img-fluid other'">
                <img v-else-if="ms.id === -2" src="@/assets/images/gssy.webp" alt=""
                    :class="ms.who === 'me' ? 'img-fluid self' : 'img-fluid other'">
                <img v-else :src="ms.photo" alt="" :class="ms.who === 'me' ? 'img-fluid self' : 'img-fluid other'">

                <div
                    :class="ms.who === 'me' ? 'self-text card text-bg-light mb-3' : 'other-text card text-bg-light mb-3'">

                    <p class="card-text">{{ ms.message }}</p>
                </div>

            </div>

        </div>
    </div>
    <div class="card-2">
        <!-- <div class="input-group">
            <span class="input-group-text">输入框</span> -->
        <!-- <textarea v-model="message" class="input form-control" rows="3"></textarea>
            <button @click="click_send" type="button" class="btn btn-outline-info btn-sm">发送</button> -->


        <!-- </div> -->
        <div class="input-group mb-3">

            <textarea v-model="message" class="form-control" id="exampleFormControlTextarea1" rows="3"></textarea>
            <!-- <button class="btn btn-outline-secondary" type="button" id="button-addon2">Button</button> -->
            <!-- <button  type="button" class="btn btn-outline-info btn-sm">发送</button> -->
            <button @click="click_send" type="button" class="btn btn-warning btn-lg">发送</button>
        </div>

    </div>




</template>



<script>

import { ref, onMounted, onUnmounted } from 'vue';
import { GameBoard } from '@/assets/script/GameBoard';
import { useStore } from 'vuex';

// import { GameBoard } from '@/assets/script/GameBoard'
//import { Chess } from '@/assets/script/chess';
export default {

    components: {

    },
    setup() {

        let parent = ref(null);
        let ctx = ref(null);
        const store = useStore();
        let message = ref("");
        const click_send = () => {

            store.state.pk.socket.send(JSON.stringify({
                event: "send-message",
                message: message.value,
                photo: store.state.user.photo,
                id: store.state.pk.content.length,
                userid: store.state.user.id
            }));

            message.value = "";
        }
        onMounted(() => {

            const game = new GameBoard(ctx.value.getContext('2d'), parent.value, store, []);

            store.commit("updateBoard", game);


            //console.log(store.state.user.color, store.state.pk.is_over);

        })

        onUnmounted(() => {
            store.commit("updateColor", "");
            store.commit("resetContent", []);
        })

        //console.log("qowuwioetuweior");
        return {
            parent,
            ctx,
            click_send,
            message
        }



    }
}

</script>

<style scoped>
#exampleFormControlTextarea1 {
    height: 50px;
    resize: none;
    font-size: 1.8vh;
}

.play {

    width: 55vw;
    height: 70vh;
    margin-top: 40px;
    margin-left: 350px;
    position: absolute;
    display: inline;

}

.input {

    margin: 0;
    resize: none;
    font-size: 1.8vh;
    background-color: transparent;
}

.btn {

    margin-left: 990px;
}


.card-2 {
    margin-left: 900px;
    margin-top: 40px;
    width: 400px;

    height: 50px;




}

.card-1 {
    margin-left: 900px;
    margin-top: 40px;
    width: 400px;

    height: 450px;


    height: calc(500px - 16vh - 1px);

    border-bottom: 1px solid lightgrey;
    overflow-y: scroll;
    user-select: text;

}

.user-message {
    width: calc(100% - 2vh);
    height: auto;
    overflow: hidden;
    margin: 0.5vh;
}

.other-text {
    display: inline-block;
    float: left;
    max-width: calc(80% - 5vh - 1vh - 1vh);
    height: auto;
    word-break: break-all;
    background-color: white;
    padding: 0.8vh;
    margin-left: 1vh;
    border-radius: 3px;
    overflow: hidden;
    font-size: 1.8vh;
}

.self-text {
    display: inline-block;
    float: right;
    max-width: calc(80% - 5vh - 1vh - 1vh);
    height: auto;
    word-break: break-all;
    background-color: #98E165;
    padding: 0.8vh;
    margin-right: 1vh;
    border-radius: 3px;
    overflow: hidden;
    font-size: 1.8vh;
}

.self {
    display: inline-block;
    float: right;
    margin-right: 1vw;
    width: 5vh;
    height: 5vh;
    user-select: none;
    margin-left: 1vw;
}

.other {
    border-radius: 50%;

    float: left;
    width: 5vh;
    height: 5vh;
    user-select: none;

}

.contro-input {
    margin-top: 1px;
    position: absolute;
    height: 50px;
    z-index: 999;
}
</style>