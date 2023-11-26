import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { userSession } from '../api/User'

export const mainStore = defineStore('main', {
  state() {
    return {
      user: {} as any,
      // imgUrl: '/api/images/book',
    }
  },
  getters: {},
  actions: {
    getUser() {
      userSession().then((r) => {
        if (r.data.status === 200) {
          this.user = r.data.data
        }
      })
    },
  },
})
