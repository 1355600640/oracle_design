import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory('/'),
  routes: [
    {
      path: '/',
      name: 'home',
      component: () => import('../views/Home.vue'),
      children: [
        {
          path: '/book',
          name: 'book',
          component: () => import('../views/Book.vue'),
        },
        {
          path: '/category',
          name: 'category',
          component: () => import('../views/Category.vue'),
        },
        {
          path: '/borrow',
          name: 'borrow',
          component: () => import('../views/Borrow.vue'),
        },
        {
          path: '/libraryCard',
          name: 'libraryCard',
          component: () => import('../views/LibraryCard.vue'),
        },
      ],
    },
    {
      path: '/login',
      name: 'login',
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/Login.vue'),
      meta: { noTop: true },
    },
  ],
})
router.beforeEach((to, from, next) => {
  if (!localStorage.getItem('oracle_token') && to.name != 'login')
    next('/login')
  next()
})
export default router
