<template>




    <div class="container">


        <!-- <div class="card c1" >
  
            <div class="card-body ">
                <div class="row">
                    <div class="col-4">
                        <img class="img-fluid" src="@/assets/images/sly.png">
                    </div>
                    
                    <div class="col-8">
                        <div class="username">{{$store.state.user.username}}</div>
                        
                    </div> -->
        <!-- </div>
            </div>
        </div> -->
        <updateUserInfo v-if="$store.state.user.updateInfo" />
        <div class="row">
            <div class="col-3 photo">
                <div class="card">
                    <div class="card-body" v-if="$store.state.user.photo === null || $store.state.user.photo === ''">
                        <img class="card-img-top" align="middle" src="@/assets/images/sly.png">
                    </div>
                    <div class="card-body" v-else>
                        <img class="card-img-top" align="middle" :src="$store.state.user.photo">
                    </div>
                </div>
            </div>
            <div class="col-6 description">
                <div class="card">
                    <div class="card-header">
                        <h3>个人信息</h3>
                    </div>
                    <div class="card-body">

                        <div class="mb-3">
                            <label class="form-label">用户名: <span class="userinfo-descript">{{
                                $store.state.user.username
                            }}</span></label>

                        </div>
                        <div class="mb-3">
                            <label class="form-label">rating: <span class="userinfo-descript">{{
                                $store.state.user.rating
                            }}</span></label>

                        </div>
                        <div class="mb-3">
                            <label for="exampleFormControlInput1" class="form-label">昵称</label>
                            <input v-model="nick" type="text" class="form-control" id="exampleFormControlInput1">
                        </div>

                        <div class="mb-3">
                            <label for="exampleFormControlTextarea1" class="form-label">个人简介</label>
                            <textarea v-model="description" class="form-control" rows="3"></textarea>
                        </div>
                        <button @click="updateInfo" type="button" class="btn btn-primary">更新信息</button>
                    </div>
                </div>
            </div>
        </div>
    </div>







</template>

<script>

import { ref } from 'vue';
import { useStore } from 'vuex';
import updateUserInfo from './UpdateUserInfo.vue';

export default {
    name: 'UserInfoView',
    components: {
        updateUserInfo
    },

    setup() {
        const store = useStore();
        let nick = ref(store.state.user.nick);
        let description = ref(store.state.user.description);
        const updateInfo = () => {

            store.dispatch("updateinfo", {
                nick: nick.value,
                description: description.value

            })
            store.commit("updateInfoStatus", true);

        }
        return {
            nick,
            description,
            updateInfo
        }

    }
}
</script>


<style scoped>
.container {
    margin: auto 0;
}

.card {
    width: 80%;
}

.userinfo-descript {
    font-size: 15px;
    color: grey;
}

img {

    height: 80%;
    background-size: cover;

}

.photo {
    margin-left: 250px;
    margin-top: 30px;
}

.description {
    margin-top: 30px;
}

label {
    display: inline-block;
    max-width: 100%;
    margin-bottom: 5px;
    font-weight: 700;
}

button {
    width: 100%;
}
</style>
