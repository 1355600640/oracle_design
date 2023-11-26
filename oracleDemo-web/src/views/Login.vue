<template>
  <div id="login">
    <form>
      <div class="center">
        <div class="title">欢迎进入图书管理系统</div>
        <div>
          <span>用户名:</span>
          <a-input
            :style="{ width: '320px' }"
            placeholder="请输入账号"
            allow-clear
            v-model="user.username"
          />
        </div>
        <div>
          <span>密码:</span>
          <a-input-password
            :style="{ width: '320px' }"
            placeholder="请输入密码"
            v-model="user.password"
            allow-clear
            @press-enter="loginUser"
            autocomplete="off"
          />
        </div>
        <div class="button-list">
          <a-button
            @click="loginUser"
            class="login"
            type="primary"
            shape="round"
            >登录</a-button
          >
          <a-button @click="regist" class="regist" type="primary" shape="round"
            >注册</a-button
          >
        </div>
      </div>
    </form>
  </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import { Message } from '@arco-design/web-vue'
import { login } from '../api/User'
import { useRouter } from 'vue-router'

let user = ref({
  username: '',
  password: '',
})

const router = useRouter()
const regist = () => {
  Message.error('请联系管理员添加账号')
}

const loginUser = () => {
  const patter = /^[0-9a-zA-z]{6,18}&/
  if (!user.value.username) {
    Message.error('请输入用户名')
  } else if (!user.value.password) {
    Message.error('请输入密码')
  } else if (patter.test(user.value.password)) {
    Message.warning('请输入以数字或字母组成的6到18位的密码')
  } else {
    login(user.value.username, user.value.password).then((r) => {
      if (r.data.status === 200) {
        localStorage.setItem('oracle_token', r.data.data.token)
        router.push('/')
        setTimeout(() => {
          Message.success('登陆成功')
        }, 0)
      }
    })
  }
}
</script>
<style lang="scss" scoped>
#login {
  width: 100%;
  height: 100vh;
  background: url('../assets/img/45ebd5b9a75f56cd14ef8cad156d1f61.jpeg')
    no-repeat center center;
  background-size: 100% 100%;
  position: relative;
  .center {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    border-radius: 16px;
    padding: 30px 40px;
    display: flex;
    gap: 20px;
    flex-direction: column;
    .title {
      font-size: 26px;
      // margin-bottom: 20px;
      font-weight: 700;
      text-align: center;
    }
    > div {
      span:first-child {
        text-align: right;
        margin-right: 10px;
        font-size: 16px;
        width: 60px;
        display: inline-block;
      }
    }
    .button-list {
      display: flex;
      justify-content: center;
      gap: 20px;
      flex-direction: row;
      > button {
        width: 120px;
        height: 35px;
      }
      .regist {
        background: white;
        border: blue 1px solid;
        color: black;

        &:hover {
          background: blue;
          color: white;
        }
      }
    }
  }
}
</style>
