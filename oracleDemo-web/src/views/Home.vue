<template>
  <div id="home">
    <HomeMenu class="homeMenu" />
    <div class="center-view">
      <RouterView style="height: 92vh " v-if="$route.name != 'home'" />
      <div v-else class="home-view">
        <div class="left">
          <div class="title">清风图书管理系统</div>
          <div style="margin: 10px 0 15px">本次系统采用下面技术实现</div>
          <a-timeline class="timeline">
            <a-timeline-item label="vue+typescript+pinia">
              前端图书管理系统
            </a-timeline-item>
            <a-timeline-item label="spring boot+mybatis+oracle">
              后端
            </a-timeline-item>
          </a-timeline>
          <div>
            <a-card hoverable class="card-demo" title="制作时间">
              <span>2023-11-23</span>
            </a-card>
          </div>
        </div>
        <div class="right">
          <a-carousel
            :style="{
              width: '100%',
              height: '400px',
            }"
            :auto-play="true"
            show-arrow="hover"
            indicator-type="line"
          >
            <a-carousel-item>
              <img
                src="../assets/img/swiper/0d84752333a315a4e2762b8d1dbb849e.jpeg"
              />
            </a-carousel-item>
            <a-carousel-item>
              <img
                src="../assets/img/swiper/45ebd5b9a75f56cd14ef8cad156d1f61.jpeg"
              />
            </a-carousel-item>
            <a-carousel-item>
              <img
                src="../assets/img/swiper/7998b93fad36fdbc1acfbb9159d97808.jpeg"
              />
            </a-carousel-item>
            <a-carousel-item>
              <img
                src="../assets/img/swiper/7998b93fad36fdbc1acfbb9159d97808.jpeg"
              />
            </a-carousel-item>
            <a-carousel-item>
              <img
                src="../assets/img/swiper/ce17991449e510c212273c854a2d7808.jpeg"
              />
            </a-carousel-item>
          </a-carousel>
        </div>
      </div>
    </div>
  </div>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { mainStore } from '../stores/counter'
import HomeMenu from '../components/HomeMenu.vue'
const router = useRouter()
let user = ref({})
const useStore = mainStore()

onMounted(() => {
  if (!localStorage.getItem('oracle_token')) {
    router.push('/login')
  }
  // getUserSession()
  useStore.getUser()
})
</script>
<style lang="scss" scoped>
#home {
  display: flex;
  background-color: #f2f2f2;
  .homeMenu {
    // flex: 3;
  }
  .center-view {
    flex: 7;

    .home-view {
      box-sizing: border-box;
      padding: 20px 20px;
      display: flex;
      gap: 20px;
      .left {
        flex: 4;
        .title {
          font-style: italic;
          font-size: 22px;
          color: #a5a5a5;
        }
      }
      .right {
        flex: 5;
        :deep .arco-carousel-slide img {
          width: 100%;
          height: 100%;
        }
      }
    }
  }
}
</style>
