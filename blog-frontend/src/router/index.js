import {createRouter, createWebHashHistory} from 'vue-router';
import request from "@/utils/request.js";

// 本地静态路由
export const constantRoutes = [
    {
        path: '/',
        component: () => import('@/views/index.vue'),
        redirect: '/home',
        children: [
            {path: '/home', component: () => import('@/views/home.vue')},
            {path: '/preview/:id', component: () => import("@/views/preview.vue"), meta: {title: '看文章'}}
        ]
    },
    {path: '/editor', component: () => import("@/views/editor.vue"), meta: {title: '写文章', needLogin: true}},
    {path: '/editor/:id', component: () => import("@/views/editor.vue"), meta: {title: '写文章', needLogin: true}}
];

// 创建路由
const router = createRouter({
    history: createWebHashHistory(),
    routes: constantRoutes,
});

//前置路由守卫 主要用于未登录时，需要登录的页面禁止访问
router.beforeEach((to, from, next) => {
    const meta = to.meta;
    if (meta && meta.needLogin) {
        //需要登录，校验当前是否已登录
        request.get("/auth/checkLoginStatus").then(result => {
            if (!result.data) {
                next('/home')
            } else {
                next();
            }
        })
    } else {
        next();
    }
});

//后置路由守卫
router.afterEach((to) => {
    if (to.meta && to.meta.title) {
        document.title = '分布式博客 - ' + to.meta.title;
    } else {
        document.title = '分布式博客';
    }
});

export default router;