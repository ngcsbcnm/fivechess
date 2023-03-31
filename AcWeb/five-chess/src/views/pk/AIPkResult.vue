<template>

    <!-- <div class="modal" tabindex="-1">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Modal title</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <p>Modal body text goes here.</p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary">Save changes</button>
                </div>
            </div>
        </div>
    </div>

    <div>qweqweqweqweqweqweqweqweqweqweqweqw</div> -->
    <div v-if="$store.state.ai.result !== ''" class="container">
        <div class="card">
            <div class="card-header">
                {{ $store.state.ai.result }}
            </div>
            <div class="card-body">
                <img v-if="$store.state.ai.result === '恭喜你赢了！！！💜💙💛💚'" src="@/assets/images/win.jpg"
                    class="bg card-img-top" alt="">


                <img v-else-if="$store.state.ai.result === '输了就输了咯~💜💙💛💚'" src="@/assets/images/lower1.png"
                    class="bg card-img-to img1" alt="">
                <!-- <h5 class="card-title">Special title treatment</h5>
                <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
                
                <a href="#" class="btn btn-primary">Go somewhere</a> -->

                <button @click="click_again" type="button" class="btn btn-success">不想玩了</button>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                <button @click="click_close" type="button" class="btn btn-secondary">关闭</button>
            </div>
        </div>
    </div>



</template>


<script>
import { useStore } from 'vuex';

export default {

    setup() {
        const store = useStore();
        const click_again = () => {
            store.commit("updateStatus", 'selecting');

            store.commit("updateAIResult", '');
            store.commit("updateSt", false);
        }

        const click_close = () => {
            store.commit("updateAIResult", '');
            store.commit("updateSt", false);
        }

        return {
            click_again,
            click_close
        }
    }
}

</script>


<style scoped>
.container {
    z-index: 999;
    position: absolute;
}

.card {
    width: 300px;
    height: 350px;
    margin: auto 40px;
    margin-top: 60px;
    margin-left: 600px;
}

.btn-success {
    margin-left: 7%;

}

.btn {
    margin-top: 5%;
}

.bg {
    width: 230px;
    height: 230px;
}

.img1 {
    margin-left: 5%;
    padding: auto;
}
</style>