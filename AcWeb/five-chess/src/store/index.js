import { createStore } from 'vuex'
import ModuleUser from './user'
import ModulePk from './pk'
import ModuleAI from './ai'
import ModuleRecord from './record'
export default createStore({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user: ModuleUser,
    pk: ModulePk,
    ai: ModuleAI,
    record: ModuleRecord
  }
})
