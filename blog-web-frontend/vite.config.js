import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
//引入eslint
import eslint from "vite-plugin-eslint";

export default defineConfig({
  plugins: [vue(),
    eslint({ lintOnStart: true, cache: false }) // 项目运行时进行eslint检查
  ],
})
