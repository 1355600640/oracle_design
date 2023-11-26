<template>
  <div id="app">
    <TopBar v-if="!$route.meta.noTop" />
    <RouterView class="routerView" />
  </div>
</template>
<script lang="ts" setup>
import TopBar from './components/TopBar.vue'
</script>
<style lang="scss">
#app {
  display: flex;
  flex-direction: column;
  height: 100vh;
  .routerView {
    flex: 1;
  }
}
</style>
