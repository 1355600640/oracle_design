<template>
  <div id="menu">
    <a-menu
      :default-selected-keys="['0']"
      style="height: 100%"
      theme="dark"
      showCollapseButton
      :selected-keys="selected"
      @menu-item-click="menuClick"
      :open-keys="openKey"
      @sub-menu-click="subClick"
      :auto-open="true"
    >
      <a-menu-item key="0">
        <template #icon><icon-home /></template>
        首页
      </a-menu-item>
      <a-sub-menu key="1">
        <template #icon><icon-settings /></template>
        <template #title>书籍管理</template>
        <a-menu-item key="1">书籍管理</a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="2">
        <template #icon><icon-message /></template>
        <template #title>书籍类型</template>
        <a-menu-item key="2">书籍类型</a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="3">
        <template #icon><icon-bg-colors /></template>
        <template #title>借阅信息管理</template>
        <a-menu-item key="3">借阅信息管理</a-menu-item>
      </a-sub-menu>
      <a-sub-menu key="4">
        <template #icon><icon-layers /></template>
        <template #title>借阅证管理</template>
        <a-menu-item key="4">借阅证管理</a-menu-item>
      </a-sub-menu>
    </a-menu>
  </div>
</template>
<script lang="ts" setup>
import { ref, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
const route = useRoute()
const router = useRouter()
let selected = ref(['0'])
let openKey = ref([])
const routerUrl = ['/', '/book', '/category', '/borrow', '/libraryCard']
const menuClick = (key) => {
  selected.value = [key]
  openKey.value = [key]
  router.push(routerUrl[key])
}
const subClick = (key, openKeys) => {
  openKey.value = openKeys
  console.log(key)
}

onMounted(() => {
  routerUrl.forEach((r, index) => {
    if (route.path == r) {
      selected.value = [index + '']
      openKey.value = [index + '']
    }
  })
})
</script>
<style lang="scss" scoped>
#menu {
  // background: #232324;
}
</style>
