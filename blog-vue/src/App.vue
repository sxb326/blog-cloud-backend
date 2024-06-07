<template>
    <div id="app">
        <el-header class="header" style="background-color: #FFF;">
            <el-row class="header-row" justify="space-between" align="middle">
                <el-col :span="5" class="header-logo">
                    <h3>分布式博客系统</h3>
                </el-col>
                <el-col :span="10" class="header-nav">
                    <router-link to="/" class="menuItem">首页</router-link>
                    <router-link to="/test" class="menuItem">专栏</router-link>
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
                <el-col :span="3" class="header-right">
                    <el-button v-if="isUserEmpty(user)" type="primary" plain @click="openLoginForm">登录/注册</el-button>
                    <div v-else class="centered-container">
                        <el-icon size="30" class="centered-item">
                            <BellFilled />
                        </el-icon>
                        <el-dropdown trigger="click">
                            <el-avatar :size="30" src="/vite.svg" class="centered-item" />
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item>写篇文章</el-dropdown-item>
                                    <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>

                    </div>
                </el-col>
            </el-row>
        </el-header>
        <el-container class="main">
            <el-main>
                <router-view />
            </el-main>
        </el-container>
    </div>
    <loginForm ref="loginFormRef" @get-auth-user="getAuthUser"></loginForm>
</template>

<script setup>
import { Search } from '@element-plus/icons-vue';
import { onMounted, reactive, ref } from 'vue';
// import request from '@/utils/request'
import { localStorage } from "@/utils/storage";
import { getCurrentInstance } from 'vue';
import loginForm from '@/components/login/loginForm.vue'

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
const loginFormRef = ref()
// let isLoginFormDialogShow = ref(false)

const openLoginForm = () => {
    loginFormRef.value.open()
}
//登录
// const login = () => {
//     const data = {'username': '1', 'password': '1'}
//     request.post('/auth/login', data).then(result => {
//         console.log(result)
//         getAuthUser()
//     })
// }

//注销
const logout = () => {
    localStorage.remove('BLOG_TOKEN')
    getAuthUser()
}

// 搜索
let keyWord = ref("");

function doSearch() {
    console.log('触发搜索,关键字:' + keyWord.value)
}


</script>

<style scoped>
.header {
    position: fixed;
    top: 0;
    left: 0;
    right: 0;
    z-index: 999;
    height: 70px;
    line-height: 70px;
    display: flex;
    align-items: center;
    padding: 0 15px;
    box-sizing: border-box;
}

.header-row {
    width: 100%;
}

.header-logo img {
    height: 20px;
}

.header-nav {
    display: flex;
    align-items: center;
}

.header-right {
    display: flex;
    align-items: center;
}

.search-input {
    width: 150px;
    margin-right: 10px;
}

.main {
    position: fixed;
    left: 0;
    right: 0;
}

#app {
    padding-top: 40px;
}

.menuItem {
    margin: 0 10px;
    cursor: pointer;
}

.menuItem:hover {
    border-bottom: 2px solid #007BFF;
}

.router-link-active {
    text-decoration: none;
    color: #646cff;
}

a {
    text-decoration: none;
    color: black;
}

.centered-container {
    display: flex;
    align-items: center;
    justify-content: center;
}

.centered-item {
    margin-right: 30px;
    cursor: pointer;
}

.centered-container :last-child {
    margin-right: 0;
}
</style>
