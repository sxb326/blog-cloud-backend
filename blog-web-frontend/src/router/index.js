import { createRouter, createWebHashHistory } from 'vue-router';

// 本地静态路由
export const constantRoutes = [
  { path: '/', component: () => import('@/views/index/index.vue') },
  { path: '/test', component: () => import('@/views/test/index.vue') },
];

// 创建路由
const router = createRouter({
  history: createWebHashHistory(),
  routes: constantRoutes,
});

export default router;