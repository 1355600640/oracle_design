<template>
  <div id="top-bar">
    <div class="title" @click="$router.push(`/`)">
      <icon-command />清风图书管理系统
    </div>
    <div class="user-session">
      <img src="../assets/img/default-avatar.jpg" alt="" />
      <div class="nickname">{{ user.nickname }}</div>
      <div class="logout">退出</div>
    </div>
    <a-modal
      v-model:visible="pageData.visible"
      @cancel="pageData.visible = false"
      @ok="logout"
      unmountOnClose
    >
      <template #title> Title </template>
      <div>
        You can customize modal body text by the current situation. This modal
        will be closed immediately once you press the OK button.
      </div>
    </a-modal>
  </div>
</template>
<script lang="ts" setup>
import { storeToRefs } from 'pinia'
import { mainStore } from '../stores/counter'
import { ref, nextTick } from 'vue'
import { useRouter } from 'vue-router'
import { Message } from '@arco-design/web-vue'
const router = useRouter()
const pageData = ref({
  visible: false,
})
const logout = () => {
  localStorage.removeItem('oracle_token')
  router.push('/login')
  nextTick(() => {
    Message.success('退出登录成功')
  })
}
const useStore = mainStore()
const { user } = storeToRefs(useStore)
</script>
<style lang="scss" scoped>
#top-bar {
  background: linear-gradient(133deg, #272727, #636363);
  display: flex;
  padding: 0 100px;
  height: 8vh;
  min-height: 40px;
  box-sizing: border-box;
  justify-content: space-between;
  align-items: center;
  box-shadow: 5px 3px 10px #a6a6a6;
  .title {
    color: white;
    font-size: 18px;
    font-style: italic;
    cursor: pointer;
  }
  .user-session {
    display: flex;
    align-items: center;
    gap: 10px;
    img {
      width: 50px;
      border-radius: 50%;
    }
    .nickname {
      font-size: 14px;
      color: white;
      cursor: pointer;
    }
    .logout {
      cursor: pointer;
      color: white;
      margin-left: 20px;
    }
  }
}
</style>
