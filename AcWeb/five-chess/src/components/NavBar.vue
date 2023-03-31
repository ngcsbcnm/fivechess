<template>
    
   
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
   <router-link class="navbar-brand" :to = "{name:'pk-index'}">五子棋</router-link>

    <div class="collapse navbar-collapse" id="navbarText">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">

          <router-link :class="route_name=='pk-index'?'nav-link active':'nav-link'" :to = "{name:'pk-index'}" >对战</router-link>

        </li>
        <li class="nav-item">

          <router-link :class="route_name=='record-index' ? 'nav-link active': 'nav-link' " :to = "{name:'record-index'}">对局列表</router-link>

        </li>
        <li class="nav-item">

          <router-link :class="route_name=='ranklist-index'? 'nav-link active' : 'nav-link' " :to = "{name:'rank-index'}">排行榜</router-link>

        </li>

        <li class="nav-item">

        <router-link :class="route_name=='user-info'? 'nav-link active' : 'nav-link' " :to = "{name:'user-info'}">个人信息</router-link>

        </li>
      </ul>
      <ul class="navbar-nav" v-if="!$store.state.user.is_login">
        <li class="nav-item">
          <router-link class="nav-link" :to = '{name:"login"}'  role="button" >
            登录
          </router-link>
          
        </li>
        <li class="nav-item">
          <router-link class="nav-link" :to = '{name:"register"}'  role="button" >
            注册
          </router-link>
          
        </li>
      </ul>

      <ul class="navbar-nav" v-else>
        <li class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
            {{ $store.state.user.username }}
          </a>
          <ul class="dropdown-menu">
            

            <li><a @click="logout" class="dropdown-item" href="#">退出</a></li>
            
          </ul>
          
        </li>
      </ul>

     
    </div>
  </div>
</nav>




</template>


<script>

import { useRoute } from 'vue-router';
import {computed} from 'vue';
import {useStore} from 'vuex';




//import {useStore} from 'vuex';

export default{
    setup(){

      //const store = useStore();
      const route = useRoute();
      const store = useStore();
      let route_name = computed(()=>route.name)

      const logout = ()=>{
          store.dispatch("logout");
          store.commit("updateDescription",{
              nick:"",
              description:""
          })
      }

      return {
        route_name,
        logout
      }
    }
}

</script>



<style scoped>

</style>

