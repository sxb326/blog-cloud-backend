import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
//路由
import router from '@/router';
//element-plus
import ElementPlus from 'element-plus';
import 'element-plus/dist/index.css';
//pinia
import { createPinia } from 'pinia';
import store from '@/store';
//store持久化存储
import { createPersistedState } from 'pinia-plugin-persistedstate';
// 配置全局api
import api from '@/api';
// 全局组件注册
import components from '@/components/index';

const app = createApp(App);

//使用路由
app.use(router);

//使用ElementPlus
app.use(ElementPlus);

//注册ElementPlus所有图标
import * as ElementPlusIconsVue from '@element-plus/icons-vue';
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}

//使用pinia
const pinia = createPinia();
pinia.use(
    createPersistedState({
      auto: true, // 启用所有 Store 默认持久化
    }),
  );
// 重写 $reset 方法 => 解决组合式api中无法使用问题
pinia.use(({ store }) => {
    const initialState = JSON.parse(JSON.stringify(store.$state));
    store.$reset = () => {
      store.$patch(initialState);
    };
  });
app.use(pinia);

//配置store
app.config.globalProperties.$store = store;

//配置全局api
app.config.globalProperties.$api = api

//全局组件注册
Object.keys(components).forEach((key) => {
  app.component(key, components[key]);
});

app.mount('#app')
