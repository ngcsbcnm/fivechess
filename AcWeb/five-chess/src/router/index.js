import { createRouter, createWebHistory } from 'vue-router'
import store from '@/store/index'
import PkIndexView from '@/views/pk/PkIndexView.vue'
import RecordIndexView from '@/views/recordlist/RecordIndexView.vue'
import RankIndexView from '@/views/ranklist/RankIndexView.vue'
import RegisterIndexView from '@/views/user/RegisterIndexView'
import LoginIndexView from '@/views/user/LoginIndexView'
import NotFoundView from '@/views/error/NotFoundView'
import UserInfoView from '@/views/user/UserInfoView'

import RecordPkView from '@/views/recordlist/RecordPkView';
const routes = [


  {
    path: "/pk/",
    name: "pk-index",
    component: PkIndexView,
    meta: {
      requestAuth: true
    }

  },


  {
    path: "/record/",
    name: "record-index",
    component: RecordIndexView,
    meta: {
      requestAuth: true
    }
  },

  {
    path: "/rank/",
    name: "rank-index",
    component: RankIndexView,
    meta: {
      requestAuth: true
    }
  },
  {
    path: "/user/login/",
    name: "login",
    component: LoginIndexView,
    meta: {
      requestAuth: false
    }
  },
  {
    path: "/user/check-record/",
    name: "check-record",
    component: RecordPkView,
    meta: {
      requestAuth: true
    }
  },

  {
    path: "/user/register/",
    name: "register",
    component: RegisterIndexView,
    meta: {
      requestAuth: false
    }
  },

  {
    path: "/user/info/",
    name: "user-info",
    component: UserInfoView,
    meta: {
      requestAuth: true
    }
  },

  {
    path: "/404/",
    name: "404",
    component: NotFoundView,
    meta: {
      requestAuth: true
    }

  },
  {
    path: "/:catchAll(.*)",
    redirect: "/404/"
  }


]

const router = createRouter({
  history: createWebHistory(),
  routes
})


router.beforeEach((to, from, next) => {
  if (to.meta.requestAuth && !store.state.user.is_login) {
    next({ name: "login" });
    //console.log("qweqweqweqweqwe");
  } else {
    next();
  }
  if (to.name !== 'pk-index') {
    store.commit('updateStatus', 'selecting');
  }
})


export default router
