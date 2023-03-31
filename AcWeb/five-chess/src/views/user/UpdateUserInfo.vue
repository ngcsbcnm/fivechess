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
    <div v-if="$store.state.user.updateInfo" class="container">
        <div class="card">
            <div class="card-header">
                尊敬的{{ $store.state.user.username }}用户,信息更新成功💜💙💛💚！
            </div>
            <div class="card-body">
                <img src="@/assets/images/OIP-C.jpg" class="bg card-img-top img1" alt="">

                <button @click="click_again" type="button" class="btn btn-success">确定</button>

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


            store.commit("updateInfoStatus", false);

        }



        return {
            click_again,

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
    height: 380px;
    margin: auto 40px;
    margin-top: 60px;
    margin-left: 600px;
}

.btn {
    text-align: center;
    width: 100%;
    margin-top: 15px;
}

.bg {
    width: 230px;
    height: 230px;
}

.img1 {
    margin-left: 5%;
    text-align: center;
}
</style>