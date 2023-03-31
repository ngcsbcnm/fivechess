
<template>

    <div class="container content-field">

        <div class="row">

            <div class="select col-3">
                <div @click="click_begin('AIpk')" class="card text-bg-success" style="color:blueviolet">
                    <div class="card-body mydiv">

                        <!-- 人机对战 -->
                        <p>人机对战</p>


                    </div>
                </div>

            </div>

            <div class="select col-3">
                <div @click="click_begin('matching')" class="card text-bg-primary ">
                    <div class="card-body mydiv ">

                        <!-- 联机对战 -->
                        <p>联机对战</p>

                    </div>
                </div>
            </div>

            <div class="select col-3">
                <div class="card text-bg-secondary">
                    <div class="card-body mydiv ">

                        <!-- 返回 -->
                        <p>尽请期待</p>


                    </div>
                </div>
            </div>
        </div>


    </div>

</template>


<script>

// import {Circle} from '@/assets/script/circle'
// import { ref } from 'vue';
import { useStore } from 'vuex';
export default {

    setup() {
        const store = useStore();
        const click_begin = model => {
            store.commit('updateStatus', model);
            if (model === 'AIpk') {
                store.commit("update_is_ai_vs_human", true);
            } else if (model === 'matching') {
                store.commit("update_is_ai_vs_human", false);
                //console.log(store.state.user);
            }

        }
        return {
            click_begin
        }
    }

}







</script>

<style scoped>
.container {
    margin: auto;
    margin-top: 25vh;
    width: 60%;
}

.select {
    text-align: center;
    margin: auto;
}

.mydiv {
    width: 200px;
    height: 200px;



}

.card {
    margin-bottom: 20px;
    cursor: pointer;
}

.card:hover {
    box-shadow: 2px 2px 10px rgb(173, 230, 200);
    transition: 500ms;
}



p {
    text-align: center;
    font-size: 24px;
    position: relative;
    top: 40%;
}
</style>