<template>
  <el-row class="topBar">
    <el-col :span="2">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </el-col>
    <el-col :span="12" class="menu">
      <div class="menuItem"><router-link to="/">首页</router-link></div>
      <div class="menuItem"><router-link to="/test">专栏</router-link></div>
    </el-col>
    <el-col :span="6">
      <el-input v-model="keyWord" style="width: 240px" placeholder="想搜索点什么呢">
        <template #suffix>
          <el-icon class="el-input__icon" style="cursor: pointer;" @click="doSearch">
            <search />
          </el-icon>
        </template>
      </el-input>
    </el-col>
    <!-- <el-col :span="1">
      <el-icon size="25px">
        <Bell />
      </el-icon>
    </el-col> -->
    <el-col :span="2">
      <el-button v-if="isUserEmpty(user)" type="primary" plain @click="login">登录/注册</el-button>
      <div v-else style="cursor: pointer;" @click="logout">{{ user.nickName }}</div>
    </el-col>
  </el-row>
  <el-button type="primary" @click="handleClick">handleClick</el-button>
  <router-view />
</template>

<script setup>
import { Search } from '@element-plus/icons-vue';
import { onMounted, reactive, ref } from 'vue';
import request from '@/utils/request'
import { localStorage } from "@/utils/storage";
import { getCurrentInstance } from 'vue';
const { proxy } = getCurrentInstance();

onMounted(getAuthUser)

//获取登录用户
let user = reactive({});

//获取当前登录用户
async function getAuthUser() {
  const response = await proxy.$api.auth.getAuthUser();
  if (response.data) {
    Object.assign(user, response.data);
  } else {
    for (const key in user) {
      if (Object.prototype.hasOwnProperty.call(user, key)) {
        delete user[key];
      }
    }
  }
}

//判断用户对象是否为空
function isUserEmpty(obj) {
  return Object.keys(obj).length === 0;
}

// 搜索
let keyWord = ref("");

function doSearch() {
  console.log('触发搜索,关键字:' + keyWord.value)
}

let res = ref("");

async function handleClick() {
  res.value = await proxy.$api.test.hello();
}

//登录
const login = () => {
  const data = { 'username': '1', 'password': '1' }
  request.post('/auth/login', data).then(result => {
    console.log(result)
    getAuthUser()
  })
}

//注销
const logout = () => {
  localStorage.remove('BLOG_TOKEN')
  getAuthUser()
}
</script>

<style scoped>
.topBar {
  border-bottom: 1px solid #afafaf;
  margin-bottom: 10px;
}

.menu {
  display: flex
}

.menuItem {
  margin: 0 10px;
  color: red;
}

.router-link-active {
  text-decoration: none;
  color: #646cff;
}

a {
  text-decoration: none;
  color: black;
}
</style>
